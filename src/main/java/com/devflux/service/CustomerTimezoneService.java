package com.devflux.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devflux.entity.CustomerTimeZoneEntity;
import com.devflux.hazelcast.cachemanager.CustomerTimezoneCacheManager;
import com.devflux.repository.CustomerTimezoneRepository;

@Service
public class CustomerTimezoneService
{
	private final CustomerTimezoneRepository CustomerTimezoneRepository;

	private final CustomerTimezoneCacheManager CustomerTimezoneCacheManager;

	public CustomerTimezoneService(CustomerTimezoneRepository CustomerTimezoneRepository, CustomerTimezoneCacheManager CustomerTimezoneCacheManager)
	{
		this.CustomerTimezoneRepository = CustomerTimezoneRepository;
		this.CustomerTimezoneCacheManager = CustomerTimezoneCacheManager;
	}

	public List<CustomerTimeZoneEntity> getAllCustomerTimezones()
	{
		return CustomerTimezoneCacheManager.getAll().stream().toList();
	}

	public CustomerTimeZoneEntity getCustomerTimezoneById(Long id)
	{
		return CustomerTimezoneCacheManager.get(id) != null ? CustomerTimezoneCacheManager.get(id)
				: CustomerTimezoneRepository.findById(id)
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer timezone not found with id: " + id));
	}

	public CustomerTimeZoneEntity createCustomerTimezone(CustomerTimeZoneEntity request)
	{
		validateRequest(request, null);

		CustomerTimeZoneEntity entity = new CustomerTimeZoneEntity();
		entity.setZoneId(request.getZoneId().trim());
		entity.setDisplayName(request.getDisplayName().trim());
		entity.setUtcOffset(request.getUtcOffset().trim());
		entity.setActive(request.isActive());

		CustomerTimeZoneEntity saved = CustomerTimezoneRepository.save(entity);
		CustomerTimezoneCacheManager.add(saved);
		return saved;
	}

	public CustomerTimeZoneEntity updateCustomerTimezone(Long id, CustomerTimeZoneEntity request)
	{
		if (id == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is required");
		}
		validateRequest(request, id);

		CustomerTimeZoneEntity entity = CustomerTimezoneRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer timezone not found with id: " + id));

		entity.setZoneId(request.getZoneId().trim());
		entity.setDisplayName(request.getDisplayName().trim());
		entity.setUtcOffset(request.getUtcOffset().trim());
		entity.setActive(request.isActive());

		CustomerTimeZoneEntity saved = CustomerTimezoneRepository.save(entity);
		CustomerTimezoneCacheManager.update(saved);
		return saved;
	}

	public CustomerTimeZoneEntity changeStatus(Long id, boolean active)
	{
		CustomerTimeZoneEntity entity = CustomerTimezoneRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer timezone not found with id: " + id));
		entity.setActive(active);
		CustomerTimeZoneEntity saved = CustomerTimezoneRepository.save(entity);
		CustomerTimezoneCacheManager.update(saved);
		return saved;
	}

	public CustomerTimeZoneEntity deleteCustomerTimezone(Long id)
	{
		CustomerTimeZoneEntity entity = CustomerTimezoneRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer timezone not found with id: " + id));
		CustomerTimezoneRepository.delete(entity);
		CustomerTimezoneCacheManager.delete(id);
		return entity;
	}

	private void validateRequest(CustomerTimeZoneEntity request, Long currentId)
	{
		if (request == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
		}
		if (request.getZoneId() == null || request.getZoneId().isBlank())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "zoneId is required");
		}
		if (request.getDisplayName() == null || request.getDisplayName().isBlank())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "displayName is required");
		}
		if (request.getUtcOffset() == null || request.getUtcOffset().isBlank())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "utcOffset is required");
		}

		String zoneId = request.getZoneId().trim();
		boolean exists = currentId == null ? CustomerTimezoneRepository.existsByZoneIdIgnoreCase(zoneId)
				: CustomerTimezoneRepository.existsByZoneIdIgnoreCaseAndIdNot(zoneId, currentId);
		if (exists)
		{
			throw new ResponseStatusException(HttpStatus.CONFLICT, "zoneId already exists: " + zoneId);
		}
	}
}