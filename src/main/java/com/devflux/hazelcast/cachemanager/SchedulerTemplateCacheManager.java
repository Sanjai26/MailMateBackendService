package com.devflux.hazelcast.cachemanager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devflux.entity.SchedulerTemplateEntity;
import com.devflux.hazelcast.util.CacheEnum;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Component
public class SchedulerTemplateCacheManager
{
	@Autowired
	private HazelcastInstance hazelcastInstance;

	public SchedulerTemplateCacheManager(HazelcastInstance hazelcastInstance)
	{
		this.hazelcastInstance = hazelcastInstance;
	}

	private IMap<Long, SchedulerTemplateEntity> getMap()
	{
		return hazelcastInstance.getMap(CacheEnum.SCHEDULER_MAIL_TEMPLATE.getValue());
	}

	// CREATE
	public void add(SchedulerTemplateEntity entity)
	{
		getMap().put(entity.getId(), entity);
	}

	// READ
	public SchedulerTemplateEntity get(Long id)
	{
		return getMap().get(id);
	}

	// READ ALL
	public Collection<SchedulerTemplateEntity> getAll()
	{
		return getMap().values();
	}

	// UPDATE
	public void update(SchedulerTemplateEntity entity)
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
