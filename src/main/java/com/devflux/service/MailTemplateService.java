package com.devflux.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devflux.entity.MailTemplateEntity;
import com.devflux.entity.UserEntity;
import com.devflux.hazelcast.cachemanager.MailTemplateCacheManager;
import com.devflux.repository.MailTemplateRepository;

@Service
public class MailTemplateService
{
	private final MailTemplateRepository	mailTemplateRepository;


	private final UserService				userService;
	
	private final MailTemplateCacheManager mailTemplateCacheManager;

	MailTemplateService(MailTemplateRepository mailTemplateRepository, UserService userService, MailTemplateCacheManager mailTemplateCacheManager)
	{
		this.mailTemplateRepository = mailTemplateRepository;
		this.userService = userService;
		this.mailTemplateCacheManager = mailTemplateCacheManager;
	}

	public MailTemplateEntity addMailTemplate(MailTemplateEntity mailTemplateEntity)
	{
		MailTemplateEntity mailTemplate = new MailTemplateEntity();
		try
		{
			UserEntity createdBy = userService.getUserIdFromAuthentication();

			mailTemplate.setTemplateName(mailTemplateEntity.getTemplateName());
			mailTemplate.setSubject(mailTemplateEntity.getSubject());
			mailTemplate.setBody(mailTemplateEntity.getBody());
			mailTemplate.setIsHtml(mailTemplateEntity.getIsHtml());
			mailTemplate.setCreatedBy(createdBy);
			mailTemplate.setCreatedAt(LocalDateTime.now());
		}
		catch (RuntimeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MailTemplateEntity save = mailTemplateRepository.save(mailTemplate);
		mailTemplateCacheManager.add(mailTemplate); // Add to cache
		return save;
	}

	public List<MailTemplateEntity> getAllMailTemplates()
	{
		return mailTemplateCacheManager.getAll().stream().toList();
	}

	public MailTemplateEntity getMailTemplateById(long id)
	{
		return mailTemplateRepository.findById(id).orElseThrow(() -> new RuntimeException("Mail template not found with id: " + id));
	}

	public void deleteMailTemplateById(long id)
	{
		if (!mailTemplateRepository.existsById(id))
		{
			throw new RuntimeException("Mail template not found with id: " + id);
		}
		mailTemplateCacheManager.delete(id); // Remove from cache
		mailTemplateRepository.deleteById(id);
	}

	public MailTemplateEntity modifyMailTemplate(MailTemplateEntity mailTemplate)
	{
		MailTemplateEntity tempMailId = getMailTemplateById(mailTemplate.getId());

		tempMailId.setTemplateName(mailTemplate.getTemplateName());
		tempMailId.setSubject(mailTemplate.getSubject());
		tempMailId.setBody(mailTemplate.getBody());
		tempMailId.setIsHtml(mailTemplate.getIsHtml());
		tempMailId.setCreatedBy(userService.getUserIdFromAuthentication());
		tempMailId.setLastUpdatedAt(LocalDateTime.now());
		mailTemplateCacheManager.update(tempMailId); // Update from cache
		return mailTemplateRepository.save(tempMailId);
	}
}
