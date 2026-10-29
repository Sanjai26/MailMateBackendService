package com.devflux.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.devflux.entity.CustomerTimeZoneEntity;
import com.devflux.entity.SchedulerMailEntity;
import com.devflux.entity.SchedulerTemplateEntity;
import com.devflux.hazelcast.cachemanager.SchedulerMailCacheManager;
import com.devflux.hazelcast.cachemanager.SchedulerTemplateCacheManager;
import com.devflux.repository.CustomerTimezoneRepository;
import com.devflux.repository.SchedulerTemplateRepository;
import com.devflux.vo.SchedulerTemplateVO;

@Service
public class SchedulerTemplateService
{

	private static final org.slf4j.Logger		OUT	= LoggerFactory.getLogger(SchedulerTemplateService.class);

	private final SchedulerTemplateRepository	schedulerTemplateRepository;

	private final SchedulerTemplateCacheManager	schedulerTemplateCacheManager;

	private final SchedulerMailCacheManager		schedulerMailCacheManager;

	private final CustomerTimezoneRepository	customerTimezoneRepository;

	public SchedulerTemplateService(SchedulerTemplateRepository schedulerTemplateRepository, SchedulerTemplateCacheManager schedulerTemplateCacheManager,
			SchedulerMailCacheManager schedulerMailCacheManager, CustomerTimezoneRepository customerTimezoneRepository)
	{
		this.schedulerTemplateRepository = schedulerTemplateRepository;
		this.schedulerTemplateCacheManager = schedulerTemplateCacheManager;
		this.schedulerMailCacheManager = schedulerMailCacheManager;
		this.customerTimezoneRepository = customerTimezoneRepository;
	}

	public SchedulerTemplateEntity addSchedulerTemplate(String scheduleName, String frequency, LocalTime scheduleTime, String dayOfWeek, Integer dayOfMonth, boolean isActive,
			Long timeZoneId)
	{

		SchedulerTemplateEntity schedulerTemplate = new SchedulerTemplateEntity();

		schedulerTemplate.setScheduleName(scheduleName);
		schedulerTemplate.setFrequency(frequency.toUpperCase(Locale.ROOT));
		schedulerTemplate.setScheduleTime(scheduleTime);
		schedulerTemplate.setDayOfWeek(dayOfWeek);
		schedulerTemplate.setDayOfMonth(dayOfMonth);
		schedulerTemplate.setIsActive(isActive);

		schedulerTemplate.setTimeZone(getCustomerTimezone(timeZoneId));

		schedulerTemplate.setCreatedAt(LocalDateTime.now());
		schedulerTemplate.setLastUpdatedAt(LocalDateTime.now());

		SchedulerTemplateEntity saved = schedulerTemplateRepository.save(schedulerTemplate);

		// Add the saved entity to the cache.
		schedulerTemplateCacheManager.add(saved);

		return saved;
	}

	public List<SchedulerTemplateEntity> getAllSchedulerTemplates()
	{
		return schedulerTemplateCacheManager.getAll().stream().toList();
	}

	public SchedulerTemplateEntity getSchedulerTemplateById(long id)
	{
		return schedulerTemplateRepository.findById(id).orElseThrow(() -> new RuntimeException("Scheduler template not found with id: " + id));
	}

	public Map<String, Object> modifySchedulerTemplate(long id, String scheduleName, String frequency, LocalTime scheduleTime, String dayOfWeek, Integer dayOfMonth,
			boolean isActive, Long timeZoneId)
	{

		SchedulerTemplateEntity schedulerTemplate = getSchedulerTemplateById(id);

		schedulerTemplate.setScheduleName(scheduleName);
		schedulerTemplate.setFrequency(frequency.toUpperCase(Locale.ROOT));
		schedulerTemplate.setScheduleTime(scheduleTime);
		schedulerTemplate.setDayOfWeek(dayOfWeek);
		schedulerTemplate.setDayOfMonth(dayOfMonth);
		schedulerTemplate.setIsActive(isActive);
		schedulerTemplate.setTimeZone(getCustomerTimezone(timeZoneId));
		schedulerTemplate.setLastUpdatedAt(LocalDateTime.now());

		// Save changes to the database
		SchedulerTemplateEntity updated = schedulerTemplateRepository.save(schedulerTemplate);

		// Update cache with the saved entity
		schedulerTemplateCacheManager.update(updated);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Scheduler template '" + updated.getScheduleName() + "' updated successfully.");
		response.put("schedulerTemplate", updated);

		return response;
	}

	public Map<String, Object> deleteSchedulerTemplateById(long id)
	{
		if (!schedulerTemplateRepository.existsById(id))
		{
			throw new RuntimeException("Scheduler template not found with id: " + id);
		}

		Map<String, Object> response = new HashMap<>();
		String templateName = schedulerTemplateCacheManager.get(id).getScheduleName();
		schedulerTemplateCacheManager.delete(id); // Remove from cache
		response.put("message", "Scheduler template '" + templateName + "' deleted successfully.");
		schedulerTemplateRepository.deleteById(id);

		return response;
	}

	public List<SchedulerTemplateVO> getSchedulerTemplateVOList()
	{

		List<SchedulerTemplateEntity> entities = getAllSchedulerTemplates();

		List<SchedulerMailEntity> schedulerMailList = schedulerMailCacheManager.getAll().stream().toList();

		Map<Long, Long> schedulerCountMap = new HashMap<>();

		// Count scheduler-mail records for each scheduler template
		for (SchedulerMailEntity schedulerMail : schedulerMailList)
		{
			if (schedulerMail.getSchedulerTemplate() == null || schedulerMail.getSchedulerTemplate().getId() == null)
			{
				continue;
			}
			Long templateId = schedulerMail.getSchedulerTemplate().getId();

			schedulerCountMap.merge(templateId, 1L, Long::sum);
		}
		List<SchedulerTemplateVO> voList = new ArrayList<>();

		// Convert entities into VO objects
		for (SchedulerTemplateEntity entity : entities)
		{

			SchedulerTemplateVO vo = new SchedulerTemplateVO();

			vo.setId(entity.getId());
			vo.setScheduleName(entity.getScheduleName());
			vo.setFrequency(entity.getFrequency());

			if (entity.getFrequency().equalsIgnoreCase("DAILY"))
			{
				vo.setScheduleDate(entity.getScheduleTime().toString());
			}
			else if (entity.getFrequency().equalsIgnoreCase("WEEKLY"))
			{

				String formattedTime = entity.getScheduleTime().format(DateTimeFormatter.ofPattern("hh:mm"));

				vo.setScheduleDate("Every " + entity.getDayOfWeek().substring(0, 1).toUpperCase() + entity.getDayOfWeek().substring(1).toLowerCase() + " at " + formattedTime);
			}
			else if (entity.getFrequency().equalsIgnoreCase("MONTHLY"))
			{

				int day = entity.getDayOfMonth();
				String daySuffix;

				if (day % 100 >= 11 && day % 100 <= 13)
				{
					daySuffix = "th";
				}
				else
				{
					switch (day % 10)
					{
					case 1:
						daySuffix = "st";
						break;
					case 2:
						daySuffix = "nd";
						break;
					case 3:
						daySuffix = "rd";
						break;
					default:
						daySuffix = "th";
					}
				}

				String formattedTime = entity.getScheduleTime().format(java.time.format.DateTimeFormatter.ofPattern("hh:mm"));

				vo.setScheduleDate(day + daySuffix + " of every month at " + formattedTime);
			}
			vo.setIsActive(entity.getIsActive());
			vo.setTimeZoneId(entity.getTimeZone() != null ? entity.getTimeZone().getId() : null);
			vo.setTimeZoneName(entity.getTimeZone() != null ? entity.getTimeZone().getDisplayName() : null);

			vo.setSchedulerCount(schedulerCountMap.getOrDefault(entity.getId(), 0L).intValue());

			voList.add(vo);
		}

		return voList;
	}

	private CustomerTimeZoneEntity getCustomerTimezone(Long id)
	{
		if (id == null)
		{
			throw new RuntimeException("Timezone id is required");
		}
		return customerTimezoneRepository.findById(id).orElseThrow(() -> new RuntimeException("Timezone not found with id: " + id));
	}
}