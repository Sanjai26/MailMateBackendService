package com.devflux.hazelcast.cachemanager;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.devflux.entity.SettingsEntity;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Component
public class SettingCacheManager
{
	private HazelcastInstance hazelcastInstance;

	SettingCacheManager(HazelcastInstance hazelcastInstance)
	{
		this.hazelcastInstance = hazelcastInstance;
	}

	private IMap<Integer, SettingsEntity> getMap()
	{
		return hazelcastInstance.getMap("settings");
	}

	// CREATE
	public void add(SettingsEntity entity)
	{
		getMap().put(entity.getId(), entity);
	}

	// READ
	public SettingsEntity get(Integer id)
	{
		return getMap().get(id);
	}

	// READ ALL
	public Collection<SettingsEntity> getAll()
	{
		return getMap().values();
	}

	// UPDATE
	public void update(SettingsEntity entity)
	{
		getMap().put(entity.getId(), entity);
	}

	// DELETE
	public void delete(Integer id)
	{
		getMap().remove(id);
	}

	// CLEAR
	public void clear()
	{
		getMap().clear();
	}
	
	// READ
	public SettingsEntity getByPropertyName(String propertyName)
	{
		return getMap().values().stream()
				.filter(entity -> entity.getPropertyName().equals(propertyName))
				.findFirst()
				.orElse(null);
	}
}
