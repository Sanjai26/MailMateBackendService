package com.devflux.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devflux.entity.MailTemplateEntity;
import com.devflux.entity.MasterUserEntity;
import com.devflux.entity.SchedulerMailEntity;
import com.devflux.entity.SchedulerTemplateEntity;
import com.devflux.repository.MailTemplateRepository;
import com.devflux.repository.MasterUserRepository;
import com.devflux.repository.SchedulerMailRepository;
import com.devflux.repository.SchedulerTemplateRepository;

@Service
public class SchedulerMailService
{
	private final SchedulerMailRepository schedulerMailRepository;
	private final SchedulerTemplateRepository schedulerTemplateRepository;
	private final MailTemplateRepository mailTemplateRepository;
	private final MasterUserRepository masterUserRepository;

	public SchedulerMailService(SchedulerMailRepository schedulerMailRepository,
			SchedulerTemplateRepository schedulerTemplateRepository, MailTemplateRepository mailTemplateRepository,
			MasterUserRepository masterUserRepository)
	{
		this.schedulerMailRepository = schedulerMailRepository;
		this.schedulerTemplateRepository = schedulerTemplateRepository;
		this.mailTemplateRepository = mailTemplateRepository;
		this.masterUserRepository = masterUserRepository;
	}

	public SchedulerMailEntity addSchedulerMail(long schedulerTemplateId, long mailTemplateId, long createdById,
			LocalDateTime lastRun, LocalDateTime nextRun, boolean isActive)
	{
		SchedulerMailEntity schedulerMail = new SchedulerMailEntity();
		schedulerMail.setSchedulerTemplate(getSchedulerTemplate(schedulerTemplateId));
		schedulerMail.setMailTemplate(getMailTemplate(mailTemplateId));
		schedulerMail.setCreatedBy(getMasterUser(createdById));
		schedulerMail.setLastRun(lastRun);
		schedulerMail.setNextRun(nextRun);
		schedulerMail.setCreatedAt(LocalDateTime.now());
		schedulerMail.setAtive(isActive);
		return schedulerMailRepository.save(schedulerMail);
	}

	public List<SchedulerMailEntity> getAllSchedulerMails()
	{
		return schedulerMailRepository.findAll();
	}

	public SchedulerMailEntity getSchedulerMailById(long id)
	{
		return schedulerMailRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Scheduler mail not found with id: " + id));
	}

	public SchedulerMailEntity modifySchedulerMail(long id, long schedulerTemplateId, long mailTemplateId,
			long createdById, LocalDateTime lastRun, LocalDateTime nextRun, boolean isActive)
	{
		SchedulerMailEntity schedulerMail = getSchedulerMailById(id);
		schedulerMail.setSchedulerTemplate(getSchedulerTemplate(schedulerTemplateId));
		schedulerMail.setMailTemplate(getMailTemplate(mailTemplateId));
		schedulerMail.setCreatedBy(getMasterUser(createdById));
		schedulerMail.setLastRun(lastRun);
		schedulerMail.setNextRun(nextRun);
		schedulerMail.setAtive(isActive);
		return schedulerMailRepository.save(schedulerMail);
	}

	public void deleteSchedulerMailById(long id)
	{
		if (!schedulerMailRepository.existsById(id))
		{
			throw new RuntimeException("Scheduler mail not found with id: " + id);
		}
		schedulerMailRepository.deleteById(id);
	}

	private SchedulerTemplateEntity getSchedulerTemplate(long id)
	{
		return schedulerTemplateRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Scheduler template not found with id: " + id));
	}

	private MailTemplateEntity getMailTemplate(long id)
	{
		return mailTemplateRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Mail template not found with id: " + id));
	}

	private MasterUserEntity getMasterUser(long id)
	{
		return masterUserRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Master user not found with id: " + id));
	}
}
