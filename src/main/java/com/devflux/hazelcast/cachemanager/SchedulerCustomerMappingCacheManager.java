package com.devflux.hazelcast.cachemanager;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devflux.entity.SchedulerCustomerMapping;
import com.devflux.hazelcast.util.CacheEnum;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Component
public class SchedulerCustomerMappingCacheManager
{
	@Autowired
	private HazelcastInstance hazelcastInstance;

	public SchedulerCustomerMappingCacheManager(HazelcastInstance hazelcastInstance)
	{
		this.hazelcastInstance = hazelcastInstance;
	}

	private IMap<Long, SchedulerCustomerMapping> getMap()
	{
		return hazelcastInstance.getMap(CacheEnum.SCHEDULER_MAIL_Customer_MAPPING.getValue());
	}

	// CREATE
	public void add(SchedulerCustomerMapping entity)
	{
		getMap().put(entity.getId(), entity);
	}

	// READ
	public SchedulerCustomerMapping get(Long id)
	{
		return getMap().get(id);
	}

	// READ ALL
	public Collection<SchedulerCustomerMapping> getAll()
	{
		return getMap().values();
	}

	// UPDATE
	public void update(SchedulerCustomerMapping entity)
	{
		getMap().put(entity.getId(), entity);
	}

	// DELETE
	public void delete(Long id)
	{
		getMap().remove(id);
	}

	// CLEAR
	public void clear()
	{
		getMap().clear();
	}
}
