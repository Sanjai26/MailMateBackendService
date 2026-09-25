package com.devflux.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devflux.entity.CustomerEntity;
import com.devflux.entity.SchedulerClientMapping;
import com.devflux.entity.SchedulerMailEntity;
import com.devflux.repository.CustomerRepository;
import com.devflux.repository.SchedulerClientMappingRepository;
import com.devflux.repository.SchedulerMailRepository;

@Service
public class SchedulerClientMappingService
{
	@Autowired
	private  SchedulerClientMappingRepository schedulerClientMappingRepository;
	@Autowired
	private  CustomerRepository clientRepository;
	@Autowired
	private  SchedulerMailRepository schedulerMailRepository;

	public SchedulerClientMapping addSchedulerClientMapping(long userId, long schedulerMailId)
	{
		SchedulerClientMapping mapping = new SchedulerClientMapping();
		mapping.setClient(getUser(userId));
		mapping.setSchedulerReport(getSchedulerMail(schedulerMailId));
		return schedulerClientMappingRepository.save(mapping);
	}

	public List<SchedulerClientMapping> getAllSchedulerClientMappings()
	{
		return schedulerClientMappingRepository.findAll();
	}

	public SchedulerClientMapping getSchedulerClientMappingById(long id)
	{
		return schedulerClientMappingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Scheduler user mapping not found with id: " + id));
	}

	public SchedulerClientMapping modifySchedulerClientMapping(long id, long userId, long schedulerMailId)
	{
		SchedulerClientMapping mapping = getSchedulerClientMappingById(id);
		mapping.setClient(getUser(userId));
		mapping.setSchedulerReport(getSchedulerMail(schedulerMailId));
		return schedulerClientMappingRepository.save(mapping);
	}

	public void deleteSchedulerClientMappingById(long id)
	{
		if (!schedulerClientMappingRepository.existsById(id))
		{
			throw new RuntimeException("Scheduler user mapping not found with id: " + id);
		}
		schedulerClientMappingRepository.deleteById(id);
	}

	private CustomerEntity getUser(long id)
	{
		return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	private SchedulerMailEntity getSchedulerMail(long id)
	{
		return schedulerMailRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Scheduler mail not found with id: " + id));
	}
}
