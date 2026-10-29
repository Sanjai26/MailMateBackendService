package com.devflux.hazelcast.cachemanager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devflux.entity.CustomerTimeZoneEntity;
import com.devflux.hazelcast.util.CacheEnum;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Component
public class CustomerTimezoneCacheManager
{
	@Autowired
	private HazelcastInstance hazelcastInstance;

	public CustomerTimezoneCacheManager(HazelcastInstance hazelcastInstance)
	{
		this.hazelcastInstance = hazelcastInstance;
	}

	private IMap<Long, CustomerTimeZoneEntity> getMap()
	{
		return hazelcastInstance.getMap(CacheEnum.TIMEZONE.getValue());
	}

	// CREATE
	public void add(CustomerTimeZoneEntity entity)
	{
		getMap().put(entity.getId(), entity);
	}

	// READ
	public CustomerTimeZoneEntity get(Long id)
	{
		return getMap().get(id);
	}

	// READ ALL
	public Collection<CustomerTimeZoneEntity> getAll()
	{
		return getMap().values();
	}

	// UPDATE
	public void update(CustomerTimeZoneEntity entity)
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