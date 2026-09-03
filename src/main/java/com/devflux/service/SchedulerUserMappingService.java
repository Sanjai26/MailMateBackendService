package com.devflux.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devflux.entity.SchedulerMailEntity;
import com.devflux.entity.SchedulerUserMapping;
import com.devflux.entity.UserEntity;
import com.devflux.repository.SchedulerMailRepository;
import com.devflux.repository.SchedulerUserMappingRepository;
import com.devflux.repository.UserRepository;

@Service
public class SchedulerUserMappingService
{
	private final SchedulerUserMappingRepository schedulerUserMappingRepository;
	private final UserRepository userRepository;
	private final SchedulerMailRepository schedulerMailRepository;

	public SchedulerUserMappingService(SchedulerUserMappingRepository schedulerUserMappingRepository,
			UserRepository userRepository, SchedulerMailRepository schedulerMailRepository)
	{
		this.schedulerUserMappingRepository = schedulerUserMappingRepository;
		this.userRepository = userRepository;
		this.schedulerMailRepository = schedulerMailRepository;
	}

	public SchedulerUserMapping addSchedulerUserMapping(long userId, long schedulerMailId)
	{
		SchedulerUserMapping mapping = new SchedulerUserMapping();
		mapping.setUserId(getUser(userId));
		mapping.setSchedulerReport(getSchedulerMail(schedulerMailId));
		return schedulerUserMappingRepository.save(mapping);
	}

	public List<SchedulerUserMapping> getAllSchedulerUserMappings()
	{
		return schedulerUserMappingRepository.findAll();
	}

	public SchedulerUserMapping getSchedulerUserMappingById(long id)
	{
		return schedulerUserMappingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Scheduler user mapping not found with id: " + id));
	}

	public SchedulerUserMapping modifySchedulerUserMapping(long id, long userId, long schedulerMailId)
	{
		SchedulerUserMapping mapping = getSchedulerUserMappingById(id);
		mapping.setUserId(getUser(userId));
		mapping.setSchedulerReport(getSchedulerMail(schedulerMailId));
		return schedulerUserMappingRepository.save(mapping);
	}

	public void deleteSchedulerUserMappingById(long id)
	{
		if (!schedulerUserMappingRepository.existsById(id))
		{
			throw new RuntimeException("Scheduler user mapping not found with id: " + id);
		}
		schedulerUserMappingRepository.deleteById(id);
	}

	private UserEntity getUser(long id)
	{
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	private SchedulerMailEntity getSchedulerMail(long id)
	{
		return schedulerMailRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Scheduler mail not found with id: " + id));
	}
}
