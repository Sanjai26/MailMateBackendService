package com.devflux.hazelcast.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.devflux.entity.CustomerEntity;
import com.devflux.entity.CustomerTimeZoneEntity;
import com.devflux.entity.MailTemplateEntity;
import com.devflux.entity.SchedulerCustomerMapping;
import com.devflux.entity.SchedulerMailEntity;
import com.devflux.entity.SchedulerTemplateEntity;
import com.devflux.entity.SettingsEntity;
import com.devflux.entity.UserEntity;
import com.devflux.hazelcast.cachemanager.SettingCacheManager;
import com.devflux.repository.CustomerRepository;
import com.devflux.repository.CustomerTimezoneRepository;
import com.devflux.repository.MailTemplateRepository;
import com.devflux.repository.SchedulerCustomerMappingRepository;
import com.devflux.repository.SchedulerMailRepository;
import com.devflux.repository.SchedulerTemplateRepository;
import com.devflux.repository.SettingsRepository;
import com.devflux.repository.UserRepository;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import jakarta.annotation.PostConstruct;

@Component
public class CacheDataLoader
{

	private final HazelcastInstance						hazelcastInstance;

	private final MailTemplateRepository				mailTemplateRepository;

	private final CustomerRepository					customerRepository;

	private final SchedulerMailRepository				schedulerMailRepository;

	private final SchedulerTemplateRepository			schedulerTemplateRepository;

	private final UserRepository						userRepository;

	private final SchedulerCustomerMappingRepository	schedulerCustomerMappingRepository;

	private final CustomerTimezoneRepository			timezoneRepository;
	
	private final SettingsRepository					settingsRepository;

	public CacheDataLoader(HazelcastInstance hazelcastInstance, UserRepository userRepository, SchedulerCustomerMappingRepository schedulerCustomerMappingRepository,
			SchedulerTemplateRepository schedulerTemplateRepository, SchedulerMailRepository schedulerMailRepository, CustomerRepository customerRepository,
			MailTemplateRepository mailTemplateRepository, CustomerTimezoneRepository timezoneRepository,SettingsRepository settingsRepository)
	{

		this.hazelcastInstance = hazelcastInstance;
		this.userRepository = userRepository;
		this.schedulerCustomerMappingRepository = schedulerCustomerMappingRepository;
		this.schedulerTemplateRepository = schedulerTemplateRepository;
		this.schedulerMailRepository = schedulerMailRepository;
		this.customerRepository = customerRepository;
		this.mailTemplateRepository = mailTemplateRepository;
		this.timezoneRepository = timezoneRepository;
		this.settingsRepository = settingsRepository;

	}

	@PostConstruct
	public void loadCache()
	{

		System.out.println("====================================");
		System.out.println("Loading master data into Hazelcast...");
		System.out.println("====================================");

		loadUserCache();
		loadTimezoneCache();
		loadMailTemplateCache();
		loadCustomerCache();
		loadSchedulerMailTemplateCache();
		loadSchedulerMailCache();
		loadSchedulerMailCustomerCache();
		loadSettingCache();
		
		System.out.println("====================================");
		System.out.println("Hazelcast cache loading completed.");
		System.out.println("====================================");
	}

	private void loadSettingCache()
	{
		IMap<Integer, SettingsEntity> settingsIMap = hazelcastInstance.getMap(CacheEnum.SETTINGS.getValue());
		List<SettingsEntity> settingsList = settingsRepository.findAll();
		for (SettingsEntity entity : settingsList)
		{
			settingsIMap.put(entity.getId(), entity);
		}
	}

	private void loadTimezoneCache()
	{
		IMap<Long, CustomerTimeZoneEntity> timezoneIMap = hazelcastInstance.getMap(CacheEnum.TIMEZONE.getValue());
		List<CustomerTimeZoneEntity> timezoneList = timezoneRepository.findAll();
		for (CustomerTimeZoneEntity entity : timezoneList)
		{
			timezoneIMap.put(entity.getId(), entity);
		}
	}

	public void loadUserCache()
	{
		IMap<Long, UserEntity> userIMap = hazelcastInstance.getMap(CacheEnum.USER.getValue());
		List<UserEntity> userList = userRepository.findAll();
		for (UserEntity mailMateEntity : userList)
		{
			userIMap.put(mailMateEntity.getId(), mailMateEntity);
		}
	}

	public void loadMailTemplateCache()
	{
		IMap<Long, MailTemplateEntity> mailTemplateIMap = hazelcastInstance.getMap(CacheEnum.MAIL_TEMPLATE.getValue());
		List<MailTemplateEntity> mailTemplateList = mailTemplateRepository.findAll();
		for (MailTemplateEntity mailMateEntity : mailTemplateList)
		{
			mailTemplateIMap.put(mailMateEntity.getId(), mailMateEntity);
		}
	}

	public void loadCustomerCache()
	{
		IMap<Long, CustomerEntity> CustomerIMap = hazelcastInstance.getMap(CacheEnum.Customer.getValue());
		List<CustomerEntity> CustomerList = customerRepository.findAll();
		for (CustomerEntity customerEntity : CustomerList)
		{
			CustomerIMap.put(customerEntity.getId(), customerEntity);
		}
	}

	public void loadSchedulerMailTemplateCache()
	{
		IMap<Long, SchedulerTemplateEntity> mailTemplateIMap = hazelcastInstance.getMap(CacheEnum.SCHEDULER_MAIL_TEMPLATE.getValue());
		List<SchedulerTemplateEntity> entities = schedulerTemplateRepository.findAll();
		Map<Integer, CustomerTimeZoneEntity> map = getTimeZoneMap();
		
		for (SchedulerTemplateEntity mailTemplateEntity : entities)
		{
			if (mailTemplateEntity.getTimeZone() != null)
			{
				CustomerTimeZoneEntity timeZoneEntity = map.get(mailTemplateEntity.getTimeZone().getId().intValue());
				mailTemplateEntity.setTimeZone(timeZoneEntity);
			}
			mailTemplateIMap.put(mailTemplateEntity.getId(), mailTemplateEntity);
		}
	}

	public void loadSchedulerMailCache()
	{
		IMap<Long, SchedulerMailEntity> schedulerMailIMap = hazelcastInstance.getMap(CacheEnum.SCHEDULER_MAIL.getValue());
		List<SchedulerMailEntity> scheduleMailList = schedulerMailRepository.findAllWithDetails();
		for (SchedulerMailEntity entity : scheduleMailList)
		{
			schedulerMailIMap.put(entity.getId(), entity);
		}
	}

	public void loadSchedulerMailCustomerCache()
	{
		IMap<Long, SchedulerCustomerMapping> schedulerMailIMap = hazelcastInstance.getMap(CacheEnum.SCHEDULER_MAIL_Customer_MAPPING.getValue());
		List<SchedulerCustomerMapping> scheduleMailList = schedulerCustomerMappingRepository.getAllSchedulerCustomerMappingDetails();
		for (SchedulerCustomerMapping entity : scheduleMailList)
		{
			schedulerMailIMap.put(entity.getId(), entity);
		}
	}
	
	private Map<Integer, CustomerTimeZoneEntity> getTimeZoneMap()
	{
		Map<Integer, CustomerTimeZoneEntity> map = new HashMap<>();
		List<CustomerTimeZoneEntity> timeList = timezoneRepository.findAll();
		for (CustomerTimeZoneEntity entity : timeList)
		{
			map.put(entity.getId().intValue(), entity);
		}
		
		return map;
	}
}
