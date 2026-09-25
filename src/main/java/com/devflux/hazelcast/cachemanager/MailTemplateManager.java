package com.devflux.hazelcast.cachemanager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devflux.entity.MailTemplateEntity;
import com.devflux.hazelcast.util.CacheEnum;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Component
public class MailTemplateManager
{
	@Autowired
	private HazelcastInstance hazelcastInstance;

	public MailTemplateManager(HazelcastInstance hazelcastInstance)
	{
		this.hazelcastInstance = hazelcastInstance;
	}

	private IMap<Long, MailTemplateEntity> getMap()
	{
		return hazelcastInstance.getMap(CacheEnum.MAIL_TEMPLATE.getValue());
	}

	// CREATE
	public void add(MailTemplateEntity entity)
	{
		getMap().put(entity.getId(), entity);
	}

	// READ
	public MailTemplateEntity get(Long id)
	{
		return getMap().get(id);
	}

	// READ ALL
	public Collection<MailTemplateEntity> getAll()
	{
		return getMap().values();
	}

	// UPDATE
	public void update(MailTemplateEntity entity)
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
