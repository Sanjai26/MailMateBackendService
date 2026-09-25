package com.devflux.hazelcast.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devflux.entity.CustomerEntity;
import com.devflux.entity.MailTemplateEntity;
import com.devflux.entity.SchedulerClientMapping;
import com.devflux.entity.SchedulerMailEntity;
import com.devflux.entity.SchedulerTemplateEntity;
import com.devflux.entity.UserEntity;
import com.devflux.repository.CustomerRepository;
import com.devflux.repository.MailTemplateRepository;
import com.devflux.repository.SchedulerClientMappingRepository;
import com.devflux.repository.SchedulerMailRepository;
import com.devflux.repository.SchedulerTemplateRepository;
import com.devflux.repository.UserRepository;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import jakarta.annotation.PostConstruct;

@Component
public class CacheDataLoader
{

	private final HazelcastInstance		hazelcastInstance;

	@Autowired
	private MailTemplateRepository		mailTemplateRepository;

	@Autowired
	private CustomerRepository			clientRepository;

	@Autowired
	private SchedulerMailRepository		schedulerMailRepository;

	@Autowired
	private SchedulerTemplateRepository	schedulerTemplateRepository;

	@Autowired
	private UserRepository				userRepository;
	
	@Autowired
	private SchedulerClientMappingRepository schedulerClientMappingRepository;

	public CacheDataLoader(HazelcastInstance hazelcastInstance)
	{

		this.hazelcastInstance = hazelcastInstance;

	}

	@PostConstruct
	public void loadCache()
	{

		System.out.println("====================================");
		System.out.println("Loading master data into Hazelcast...");
		System.out.println("====================================");

		loadUserCache();
		loadMailTemplateCache();
		loadClientCache();
		loadSchedulerMailTemplateCache();
		loadSchedulerMailCache();
		loadSchedulerMailClientCache();

		System.out.println("====================================");
		System.out.println("Hazelcast cache loading completed.");
		System.out.println("====================================");
	}

	public void loadUserCache()
	{

		IMap<Long, UserEntity> userIMap = hazelcastInstance.getMap("users");
		List<UserEntity> userList = userRepository.findAll();
		for (UserEntity mailMateEntity : userList)
		{
			userIMap.put(mailMateEntity.getId(), mailMateEntity);
		}

	}

	public void loadMailTemplateCache()
	{
		IMap<Long, MailTemplateEntity> mailTemplateIMap = hazelcastInstance.getMap("mail-templates");
		List<MailTemplateEntity> mailTemplateList = mailTemplateRepository.findAll();
		for (MailTemplateEntity mailMateEntity : mailTemplateList)
		{
			mailTemplateIMap.put(mailMateEntity.getId(), mailMateEntity);
		}
	}

	public void loadClientCache()
	{
		IMap<Long, CustomerEntity> clientIMap = hazelcastInstance.getMap("client");
		List<CustomerEntity> clientList = clientRepository.findAll();
		for (CustomerEntity customerEntity : clientList)
		{
			clientIMap.put(customerEntity.getId(), customerEntity);
		}
	}

	public void loadSchedulerMailTemplateCache()
	{
		IMap<Long, SchedulerTemplateEntity> mailTemplateIMap = hazelcastInstance.getMap("scheduler-mail-template");
		List<SchedulerTemplateEntity> clientList = schedulerTemplateRepository.findAll();
		for (SchedulerTemplateEntity mailTemplateEntity : clientList)
		{
			mailTemplateIMap.put(mailTemplateEntity.getId(), mailTemplateEntity);
		}
	}

	public void loadSchedulerMailCache()
	{
		IMap<Long, SchedulerMailEntity> schedulerMailIMap = hazelcastInstance.getMap("scheduler-mail");
		List<SchedulerMailEntity> scheduleMailList = schedulerMailRepository.findAllWithDetails();
		for (SchedulerMailEntity entity : scheduleMailList)
		{
			schedulerMailIMap.put(entity.getId(), entity);
		}
	}
	
	public void loadSchedulerMailClientCache()
	{
		IMap<Long, SchedulerClientMapping> schedulerMailIMap = hazelcastInstance.getMap("scheduler_client_mapping");
		List<SchedulerClientMapping> scheduleMailList = schedulerClientMappingRepository.getAllSchedulerClientMappingDetails();
		for (SchedulerClientMapping entity : scheduleMailList)
		{
			schedulerMailIMap.put(entity.getId(), entity);
		}
	}
}
