package com.devflux.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devflux.entity.ServiceMasterEntity;
import com.devflux.repository.ServiceMasterRepository;

@Service
public class ServiceMasterService
{
	private final ServiceMasterRepository serviceMasterRepository;

	public ServiceMasterService(ServiceMasterRepository serviceMasterRepository)
	{
		this.serviceMasterRepository = serviceMasterRepository;
	}

	public ServiceMasterEntity addServiceMaster(long userId, String serviceName, String description)
	{
		ServiceMasterEntity serviceMaster = new ServiceMasterEntity();
		serviceMaster.setUserId(userId);
		serviceMaster.setServiceName(serviceName);
		serviceMaster.setDescription(description);
		serviceMaster.setCreatedAt(LocalDateTime.now());
		return serviceMasterRepository.save(serviceMaster);
	}

	public List<ServiceMasterEntity> getAllServiceMasters()
	{
		return serviceMasterRepository.findAll();
	}

	public ServiceMasterEntity getServiceMasterById(long id)
	{
		return serviceMasterRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Service master not found with id: " + id));
	}

	public ServiceMasterEntity modifyServiceMaster(long id, long userId, String serviceName, String description)
	{
		ServiceMasterEntity serviceMaster = getServiceMasterById(id);
		serviceMaster.setUserId(userId);
		serviceMaster.setServiceName(serviceName);
		serviceMaster.setDescription(description);
		return serviceMasterRepository.save(serviceMaster);
	}

	public void deleteServiceMasterById(long id)
	{
		if (!serviceMasterRepository.existsById(id))
		{
			throw new RuntimeException("Service master not found with id: " + id);
		}
		serviceMasterRepository.deleteById(id);
	}
}
