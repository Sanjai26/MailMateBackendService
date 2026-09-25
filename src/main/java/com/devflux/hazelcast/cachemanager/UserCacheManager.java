package com.devflux.hazelcast.cachemanager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devflux.entity.UserEntity;
import com.devflux.hazelcast.util.CacheEnum;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Component
public class UserCacheManager
{
	@Autowired
	private HazelcastInstance hazelcastInstance;

	public UserCacheManager(HazelcastInstance hazelcastInstance)
	{
		this.hazelcastInstance = hazelcastInstance;
	}

	private IMap<Long, UserEntity> getMap()
	{
		return hazelcastInstance.getMap(CacheEnum.USER.getValue());
	}

	// CREATE
	public void add(UserEntity entity)
	{
		getMap().put(entity.getId(), entity);
	}

	// READ
	public UserEntity get(Long id)
	{
		return getMap().get(id);
	}

	// READ ALL
	public Collection<UserEntity> getAll()
	{
		return getMap().values();
	}

	// UPDATE
	public void update(UserEntity entity)
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
