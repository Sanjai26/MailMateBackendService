package com.devflux.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devflux.entity.CustomerEntity;
import com.devflux.entity.SchedulerCustomerMapping;
import com.devflux.entity.SchedulerMailEntity;
import com.devflux.repository.CustomerRepository;
import com.devflux.repository.SchedulerCustomerMappingRepository;
import com.devflux.repository.SchedulerMailRepository;

@Service
public class SchedulerCustomerMappingService
{
	private final  SchedulerCustomerMappingRepository schedulerCustomerMappingRepository;
	private final  CustomerRepository CustomerRepository;
	private final  SchedulerMailRepository schedulerMailRepository;

	SchedulerCustomerMappingService(SchedulerCustomerMappingRepository schedulerCustomerMappingRepository, CustomerRepository CustomerRepository, SchedulerMailRepository schedulerMailRepository) {
		this.schedulerCustomerMappingRepository = schedulerCustomerMappingRepository;
		this.CustomerRepository = CustomerRepository;
		this.schedulerMailRepository = schedulerMailRepository;
	}

	public SchedulerCustomerMapping addSchedulerCustomerMapping(long userId, long schedulerMailId)
	{
		SchedulerCustomerMapping mapping = new SchedulerCustomerMapping();
		mapping.setCustomer(getUser(userId));
		mapping.setSchedulerReport(getSchedulerMail(schedulerMailId));
		return schedulerCustomerMappingRepository.save(mapping);
	}

	public List<SchedulerCustomerMapping> getAllSchedulerCustomerMappings()
	{
		return schedulerCustomerMappingRepository.findAll();
	}

	public SchedulerCustomerMapping getSchedulerCustomerMappingById(long id)
	{
		return schedulerCustomerMappingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Scheduler user mapping not found with id: " + id));
	}

	public SchedulerCustomerMapping modifySchedulerCustomerMapping(long id, long userId, long schedulerMailId)
	{
		SchedulerCustomerMapping mapping = getSchedulerCustomerMappingById(id);
		mapping.setCustomer(getUser(userId));
		mapping.setSchedulerReport(getSchedulerMail(schedulerMailId));
		return schedulerCustomerMappingRepository.save(mapping);
	}

	public void deleteSchedulerCustomerMappingById(long id)
	{
		if (!schedulerCustomerMappingRepository.existsById(id))
		{
			throw new RuntimeException("Scheduler user mapping not found with id: " + id);
		}
		schedulerCustomerMappingRepository.deleteById(id);
	}

	private CustomerEntity getUser(long id)
	{
		return CustomerRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	private SchedulerMailEntity getSchedulerMail(long id)
	{
		return schedulerMailRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Scheduler mail not found with id: " + id));
	}
}
