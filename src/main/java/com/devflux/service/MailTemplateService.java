package com.devflux.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devflux.entity.MailTemplateEntity;
import com.devflux.entity.UserEntity;
import com.devflux.repository.MailTemplateRepository;
import com.devflux.repository.UserRepository;

@Service
public class MailTemplateService
{
	@Autowired
	private MailTemplateRepository	mailTemplateRepository;

	@Autowired
	private UserRepository			userRepository;


	public MailTemplateEntity addMailTemplate(String templateName, String subject, String body, Boolean isHtml, long createdById)
	{
		MailTemplateEntity mailTemplate = new MailTemplateEntity();
		try
		{
			UserEntity createdBy = userRepository.findById(createdById).orElseThrow(() -> new RuntimeException("Master user not found with id: " + createdById));

			mailTemplate.setTemplateName(templateName);
			mailTemplate.setSubject(subject);
			mailTemplate.setBody(body);
			mailTemplate.setIsHtml(isHtml);
			mailTemplate.setCreatedBy(createdBy);
			mailTemplate.setCreatedAt(LocalDateTime.now());
		}
		catch (RuntimeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mailTemplateRepository.save(mailTemplate);
	}

	public List<MailTemplateEntity> getAllMailTemplates()
	{
		return mailTemplateRepository.findAll();
	}

	public MailTemplateEntity getMailTemplateById(long id)
	{
		return mailTemplateRepository.findById(id).orElseThrow(() -> new RuntimeException("Mail template not found with id: " + id));
	}

	public MailTemplateEntity modifyMailTemplate(long id, String templateName, String subject, String body, Boolean isHtml, long createdById)
	{
		MailTemplateEntity mailTemplate = getMailTemplateById(id);
		UserEntity createdBy = userRepository.findById(createdById).orElseThrow(() -> new RuntimeException("Master user not found with id: " + createdById));

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
