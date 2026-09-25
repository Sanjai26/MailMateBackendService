package com.devflux.hazelcast.cachemanager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devflux.entity.CustomerEntity;
import com.devflux.hazelcast.util.CacheEnum;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Component
public class CustomerCacheManager
{
	@Autowired
	private HazelcastInstance hazelcastInstance;

	private IMap<Long, CustomerEntity> getMap()
	{
		return hazelcastInstance.getMap(CacheEnum.CLIENT.getValue());
	}

	// CREATE
	public void add(CustomerEntity entity)
	{
		getMap().put(entity.getId(), entity);
	}

	// READ
	public CustomerEntity get(Long id)
	{
		return getMap().get(id);
	}

	// READ ALL
	public Collection<CustomerEntity> getAll()
	{
		return getMap().values();
	}

	// UPDATE
	public void update(CustomerEntity entity)
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
