package com.devflux.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devflux.entity.MailTemplateEntity;
import com.devflux.entity.MasterUserEntity;
import com.devflux.repository.MailTemplateRepository;
import com.devflux.repository.MasterUserRepository;

@Service
public class MailTemplateService
{
	private final MailTemplateRepository mailTemplateRepository;
	private final MasterUserRepository masterUserRepository;

	public MailTemplateService(MailTemplateRepository mailTemplateRepository, MasterUserRepository masterUserRepository)
	{
		this.mailTemplateRepository = mailTemplateRepository;
		this.masterUserRepository = masterUserRepository;
	}

	public MailTemplateEntity addMailTemplate(String templateName, String subject, String body, Boolean isHtml,
			long createdById)
	{
		MasterUserEntity createdBy = masterUserRepository.findById(createdById)
				.orElseThrow(() -> new RuntimeException("Master user not found with id: " + createdById));

		MailTemplateEntity mailTemplate = new MailTemplateEntity();
		mailTemplate.setTemplateName(templateName);
		mailTemplate.setSubject(subject);
		mailTemplate.setBody(body);
		mailTemplate.setIsHtml(isHtml);
		mailTemplate.setCreatedBy(createdBy);
		mailTemplate.setCreatedAt(LocalDateTime.now());
		return mailTemplateRepository.save(mailTemplate);
	}

	public List<MailTemplateEntity> getAllMailTemplates()
	{
		return mailTemplateRepository.findAll();
	}

	public MailTemplateEntity getMailTemplateById(long id)
	{
		return mailTemplateRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Mail template not found with id: " + id));
	}

	public MailTemplateEntity modifyMailTemplate(long id, String templateName, String subject, String body,
			Boolean isHtml, long createdById)
	{
		MailTemplateEntity mailTemplate = getMailTemplateById(id);
		MasterUserEntity createdBy = masterUserRepository.findById(createdById)
				.orElseThrow(() -> new RuntimeException("Master user not found with id: " + createdById));

		mailTemplate.setTemplateName(templateName);
		mailTemplate.setSubject(subject);
		mailTemplate.setBody(body);
		mailTemplate.setIsHtml(isHtml);
		mailTemplate.setCreatedBy(createdBy);
		return mailTemplateRepository.save(mailTemplate);
	}

	public void deleteMailTemplateById(long id)
	{
		if (!mailTemplateRepository.existsById(id))
		{
			throw new RuntimeException("Mail template not found with id: " + id);
		}
		mailTemplateRepository.deleteById(id);
	}
}
