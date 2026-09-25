package com.devflux.hazelcast.cachemanager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devflux.entity.SchedulerMailEntity;
import com.devflux.hazelcast.util.CacheEnum;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Component
public class SchedulerMailManager
{
	@Autowired
	private HazelcastInstance hazelcastInstance;

	public SchedulerMailManager(HazelcastInstance hazelcastInstance)
	{
		this.hazelcastInstance = hazelcastInstance;
	}

	private IMap<Long, SchedulerMailEntity> getMap()
	{
		return hazelcastInstance.getMap(CacheEnum.SCHEDULER_MAIL.getValue());
	}

	// CREATE
	public void add(SchedulerMailEntity entity)
	{
		getMap().put(entity.getId(), entity);
	}

	// READ
	public SchedulerMailEntity get(Long id)
	{
		return getMap().get(id);
	}

	// READ ALL
	public Collection<SchedulerMailEntity> getAll()
	{
		return getMap().values();
	}

	// UPDATE
	public void update(SchedulerMailEntity entity)
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
