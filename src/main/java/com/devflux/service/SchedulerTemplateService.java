package com.devflux.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devflux.entity.SchedulerTemplateEntity;
import com.devflux.repository.SchedulerTemplateRepository;

@Service
public class SchedulerTemplateService
{
	private final SchedulerTemplateRepository schedulerTemplateRepository;

	public SchedulerTemplateService(SchedulerTemplateRepository schedulerTemplateRepository)
	{
		this.schedulerTemplateRepository = schedulerTemplateRepository;
	}

	public SchedulerTemplateEntity addSchedulerTemplate(String scheduleName, String frequency, String scheduleDate,
			boolean isActive)
	{
		SchedulerTemplateEntity schedulerTemplate = new SchedulerTemplateEntity();
		schedulerTemplate.setScheduleName(scheduleName);
		schedulerTemplate.setFrequency(frequency);
		schedulerTemplate.setScheduleDate(scheduleDate);
		schedulerTemplate.setIsActive(isActive);
		schedulerTemplate.setCreatedAt(LocalDateTime.now());
		return schedulerTemplateRepository.save(schedulerTemplate);
	}

	public List<SchedulerTemplateEntity> getAllSchedulerTemplates()
	{
		return schedulerTemplateRepository.findAll();
	}

	public SchedulerTemplateEntity getSchedulerTemplateById(long id)
	{
		return schedulerTemplateRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Scheduler template not found with id: " + id));
	}

	public SchedulerTemplateEntity modifySchedulerTemplate(long id, String scheduleName, String frequency,
			String scheduleDate, boolean isActive)
	{
		SchedulerTemplateEntity schedulerTemplate = getSchedulerTemplateById(id);
		schedulerTemplate.setScheduleName(scheduleName);
		schedulerTemplate.setFrequency(frequency);
		schedulerTemplate.setScheduleDate(scheduleDate);
		schedulerTemplate.setIsActive(isActive);
		return schedulerTemplateRepository.save(schedulerTemplate);
	}

	public void deleteSchedulerTemplateById(long id)
	{
		if (!schedulerTemplateRepository.existsById(id))
		{
			throw new RuntimeException("Scheduler template not found with id: " + id);
		}
		schedulerTemplateRepository.deleteById(id);
	}
}
