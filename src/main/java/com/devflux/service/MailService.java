package com.devflux.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.angus.mail.imap.IMAPFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.devflux.encryption.EncryptionUtil;
import com.devflux.entity.SenderAccount;
import com.devflux.hazelcast.cachemanager.SettingCacheManager;

import jakarta.mail.Address;
import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

// Spring Boot 3 / jakarta: replace javax.mail.* with jakarta.mail.*
// and com.sun.mail.imap.IMAPFolder with org.eclipse.angus.mail.imap.IMAPFolder

@Service
public class MailService
{
	private static final Logger					OUT					= LoggerFactory.getLogger(MailService.class);

	private static final int					THREADS				= 5;

	private static final int					CHUNK_SIZE			= 500;

	private static final int					IMAP_APPEND_BATCH	= 50;

	private final SettingCacheManager			settingCacheManager;

	private static final Map<String, String>	IMAP_HOSTS			= Map.of("smtpout.secureserver.net", "imap.secureserver.net",											// GoDaddy
			"smtp.zoho.in", "imap.zoho.in", "smtp.zoho.com", "imap.zoho.com", "smtp.hostinger.com", "imap.hostinger.com", "smtp.office365.com", "outlook.office365.com");

	MailService(SettingCacheManager settingCacheManager)
	{
		this.settingCacheManager = settingCacheManager;
	}

	public List<String> send(SenderAccount sender, String subject, String html, List<String> recipients, boolean isHtml)
	{
		List<String> sentOk = Collections.synchronizedList(new ArrayList<>());
		if (recipients == null || recipients.isEmpty())
		{
			return sentOk;
		}

		Properties props = buildProperties();
		String host = props.getProperty("mail.smtp.host");
		String user = sender.getEmail();
		String pwd = EncryptionUtil.decrypt(sender.getPassword()); // decrypt
																	// ONCE
		String imapHost = resolveImapHost(host); // null => skip IMAP

		ExecutorService pool = Executors.newFixedThreadPool(THREADS);
		List<Future<?>> futures = new ArrayList<>();

		try
		{
			for (List<String> chunk : partition(recipients, CHUNK_SIZE))
			{
				futures.add(pool.submit(() -> sendChunk(props, host, imapHost, user, pwd, subject, html, chunk, sentOk, isHtml)));
			}

			for (Future<?> f : futures)
			{
				try
				{
					f.get();
				}
				catch (ExecutionException e)
				{
					OUT.error("Chunk task failed", e.getCause());
				}
			}
		}
		catch (InterruptedException e)
		{
			OUT.warn("Mail sending interrupted. Sent so far: {}/{}", sentOk.size(), recipients.size());
			futures.forEach(f -> f.cancel(true));
			pool.shutdownNow(); // stops pending chunks
			Thread.currentThread().interrupt(); // restore the flag
		}
		finally
		{
			pool.shutdown(); // no-op if already shut down
		}

		OUT.info("Sent {}/{} mails from {}", sentOk.size(), recipients.size(), user);
		return sentOk;
	}

	// ------------------------------------------------------------------
	// One chunk = one SMTP connection + one IMAP connection (own Session each,
	// Transport is not thread-safe)
	// ------------------------------------------------------------------
	private void sendChunk(Properties baseProps, String host, String imapHost, String user, String pwd, String subject, String body, List<String> chunk, List<String> sentOk,
			boolean isHtml)
	{
		// copy so we don't mutate shared props across parallel chunks
		Properties props = new Properties();
		props.putAll(baseProps);
		// props.putIfAbsent("mail.smtp.connectiontimeout", "10000");
		// props.putIfAbsent("mail.smtp.timeout", "20000");
		// props.putIfAbsent("mail.smtp.writetimeout", "20000");

		final String domain = user.substring(user.indexOf('@') + 1);
		Session session = Session.getInstance(props);
		Store store = null;
		Folder sent = null;

		try (Transport transport = session.getTransport("smtp"))
		{
			transport.connect(host, user, pwd);

			// IMAP is optional: failure here must NOT stop sending
			if (imapHost != null)
			{
				try
				{
					Session imapSession = Session.getInstance(buildImapProperties(imapHost));
					store = imapSession.getStore("imaps");
					store.connect(imapHost, user, pwd);
					sent = resolveSentFolder(store);
					sent.open(Folder.READ_WRITE);
				}
				catch (Exception e)
				{
					OUT.error("IMAP connect failed, mails will be sent without Sent-folder copy", e);
					sent = null;
				}
			}

			String plain = isHtml ? htmlToText(body) : body;

			for (int i = 0; i < chunk.size(); i++)
			{
				String to = chunk.get(i);
				try
				{
					if (!transport.isConnected())
					{
						transport.connect(host, user, pwd);
					}

					// Message-ID with your own domain
					MimeMessage message = new MimeMessage(session)
					{
						@Override
						protected void updateMessageID() throws MessagingException
						{
							setHeader("Message-ID", "<" + UUID.randomUUID() + "@" + domain + ">");
						}
					};

					message.setFrom(new InternetAddress(user));
					message.setReplyTo(new Address[] { new InternetAddress(user) });
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
					message.setSubject(subject, "UTF-8");
					message.setSentDate(new Date());
					message.setHeader("List-Unsubscribe", "<mailto:" + user + "?subject=unsubscribe>");

					if (isHtml)
					{
						// multipart/alternative: plain text first, HTML last
						MimeBodyPart textPart = new MimeBodyPart();
						textPart.setText(plain, "UTF-8");
						MimeBodyPart htmlPart = new MimeBodyPart();
						htmlPart.setContent(body, "text/html; charset=utf-8");

						MimeMultipart mp = new MimeMultipart("alternative");
						mp.addBodyPart(textPart);
						mp.addBodyPart(htmlPart);
						message.setContent(mp);
					}
					else
					{
						message.setText(body, "UTF-8");
					}

					message.saveChanges();
					transport.sendMessage(message, message.getAllRecipients());
					sentOk.add(to);
					OUT.info("Mail sent: {} -> {}", user, to);

					if (sent != null)
					{
						try
						{
							message.setFlag(Flags.Flag.SEEN, true);
							appendToSent(sent, message);
						}
						catch (Exception e)
						{
							OUT.warn("Sent-folder copy failed for {}", to, e);
						}
					}
				}
				catch (Exception e)
				{
					OUT.error("Failed to send email to: {}", to, e);
				}

				// throttle: 5-15s between mails (skip after last)
				if (i < chunk.size() - 1)
				{
					try
					{
						Thread.sleep(ThreadLocalRandom.current().nextLong(5000, 15000));
					}
					catch (InterruptedException ie)
					{
						Thread.currentThread().interrupt();
						break;
					}
				}
			}
		}
		catch (Exception e)
		{
			OUT.error("Chunk connection failed", e);
		}
		finally
		{
			closeQuietly(sent, store);
		}
	}

	private static String htmlToText(String html)
	{
		return html.replaceAll("(?i)<br\\s*/?>", "\n").replaceAll("(?i)</p>", "\n\n").replaceAll("<[^>]+>", "").replace("&nbsp;", " ").replace("&amp;", "&").trim();
	}

	// ------------------------------------------------------------------
	// IMAP helpers
	// ------------------------------------------------------------------
	private void appendToSent(Folder sent, Message msgs)
	{

		try
		{
			sent.appendMessages(new Message[] { msgs });
		}
		catch (Exception e)
		{
			// mail is already sent, so just log
			OUT.error("Failed to append to Sent folder: {}", sent.getFullName(), e);
		}
	}

	private Folder resolveSentFolder(Store store) throws MessagingException
	{
		// 1. RFC 6154 \Sent attribute (most reliable)
		for (Folder f : store.getDefaultFolder().list("*"))
		{
			if (f instanceof IMAPFolder)
			{
				for (String attr : ((IMAPFolder) f).getAttributes())
				{
					if ("\\Sent".equalsIgnoreCase(attr))
					{
						return f;
					}
				}
			}
		}
		// 2. common names
		for (String name : new String[] { "Sent", "INBOX.Sent", "Sent Items", "Sent Messages" })
		{
			Folder f = store.getFolder(name);
			if (f.exists())
			{
				return f;
			}
		}
		// 3. create it
		Folder f = store.getFolder("Sent");
		f.create(Folder.HOLDS_MESSAGES);
		System.out.println("Created Sent folder: " + f.getFullName());
		return f;
	}

	private Properties buildImapProperties(String imapHost)
	{
		Properties p = new Properties();
		p.put("mail.store.protocol", "imaps");
		p.put("mail.imaps.host", imapHost);
		p.put("mail.imaps.port", "993");
		p.put("mail.imaps.ssl.enable", "true");
		p.put("mail.imaps.connectiontimeout", "10000");
		p.put("mail.imaps.timeout", "20000");
		return p;
	}

	/**
	 * smtp.zoho.in -> imap.zoho.in. Returns null for Gmail (it auto-saves to
	 * Sent over SMTP).
	 */
	private String resolveImapHost(String smtpHost)
	{
		if (smtpHost == null || smtpHost.toLowerCase().contains("gmail"))
		{
			return null;
		}
		String mapped = IMAP_HOSTS.get(smtpHost.toLowerCase());
		if (mapped != null)
		{
			return mapped;
		}
		return smtpHost.replaceFirst("^smtp", "imap"); // fallback guess
	}

	private void closeQuietly(Folder sent, Store store)
	{
		try
		{
			if (sent != null && sent.isOpen())
			{
				sent.close(false);
			}
		}
		catch (Exception ignored)
		{
		}
		try
		{
			if (store != null && store.isConnected())
			{
				store.close();
			}
		}
		catch (Exception ignored)
		{
		}
	}

	private Properties buildProperties()
	{
		Properties properties = new Properties();
		if (settingCacheManager.getByPropertyName("mailmate.smtp.host") != null
				&& settingCacheManager.getByPropertyName("mailmate.smtp.host").getPropertyValue().equals("smtpout.secureserver.net"))
		{
			properties.put("mail.smtp.host", settingCacheManager.getByPropertyName("mailmate.smtp.host").getPropertyValue());
			properties.put("mail.smtp.ssl.trust", settingCacheManager.getByPropertyName("mailmate.smtp.host").getPropertyValue());
		}
		else if (settingCacheManager.getByPropertyName("mailmate.smtp.host") != null
				&& settingCacheManager.getByPropertyName("mailmate.smtp.host").getPropertyValue().equals("smtp.gmail.com"))
		{
			properties.put("mail.smtp.host", settingCacheManager.getByPropertyName("mailmate.smtp.host").getPropertyValue());
			properties.put("mail.smtp.ssl.trust", settingCacheManager.getByPropertyName("mailmate.smtp.host").getPropertyValue());
		}
		else
		{
			properties.put("mail.smtp.host", settingCacheManager.getByPropertyName("mailmate.smtp.host").getPropertyValue());
			properties.put("mail.smtp.ssl.trust", settingCacheManager.getByPropertyName("mailmate.smtp.host").getPropertyValue());
		}
		if (settingCacheManager.getByPropertyName("mailmate.smtp.port") != null)
		{
			properties.put("mail.smtp.port", settingCacheManager.getByPropertyName("mailmate.smtp.port").getPropertyValue());
		}

		if (settingCacheManager.getByPropertyName("mailmate.smtp.auth") != null)
		{
			properties.put("mail.smtp.auth", settingCacheManager.getByPropertyName("mailmate.smtp.auth").getPropertyValue());
		}

		if (settingCacheManager.getByPropertyName("mailmate.smtp.security") != null)
		{
			if (settingCacheManager.getByPropertyName("mailmate.smtp.security").getPropertyValue().equalsIgnoreCase("SSL"))
			{
				properties.put("mail.smtp.ssl.enable", "true");
			}
			else if (settingCacheManager.getByPropertyName("mailmate.smtp.security").getPropertyValue().equalsIgnoreCase("STARTTLS"))
			{
				properties.put("mail.smtp.starttls.enable", "true");
			}
		}
		return properties;
	}

	private static <T> List<List<T>> partition(List<T> list, int size)
	{
		List<List<T>> parts = new ArrayList<>();
		for (int i = 0; i < list.size(); i += size)
		{
			parts.add(list.subList(i, Math.min(i + size, list.size())));
		}
		return parts;
	}

	public boolean testSendMail(String toEmail)
	{
		SenderAccount sender = new SenderAccount();
		sender.setEmail(settingCacheManager.getByPropertyName("mailmate.smtp.username").getPropertyValue());
		sender.setPassword(settingCacheManager.getByPropertyName("mailmate.smtp.password").getPropertyValue());
		String subject = "Test Email from MailMate";
		String html = "<h1>Test Email</h1><p>This is a test</p>";
		List<String> recipients = new ArrayList<>();
		recipients.add(toEmail);
		List<String> send = send(sender, subject, html, recipients, true);
		System.out.println("Test email sent to: " + send);

		if (send.contains(toEmail))
		{
			return true;
		}
		return false;
	}
}