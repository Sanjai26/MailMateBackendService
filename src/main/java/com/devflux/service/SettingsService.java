package com.devflux.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devflux.encryption.EncryptionUtil;
import com.devflux.entity.SettingsEntity;
import com.devflux.hazelcast.cachemanager.SettingCacheManager;
import com.devflux.repository.SettingsRepository;

@Service
public class SettingsService
{
	private final SettingsRepository settingsRepository;

	private final SettingCacheManager settingCacheManager;

	SettingsService(SettingsRepository settingsRepository, SettingCacheManager settingCacheManager)
	{
		this.settingsRepository = settingsRepository;
		this.settingCacheManager = settingCacheManager;
	}

	public SettingsEntity createSetting(SettingsEntity settingsEntity)
	{
		SettingsEntity save = settingsRepository.save(settingsEntity);
		settingCacheManager.add(save);
		return save;
	}

	public List<SettingsEntity> getAllSettings()
	{
		return settingCacheManager.getAll().stream().toList();
	}

	public SettingsEntity getSettingById(Integer id)
	{
		SettingsEntity cachedEntity = settingCacheManager.get(id);
		if (cachedEntity != null)
		{
			return cachedEntity;
		}

		return settingsRepository.findById(id).orElseThrow(() -> new RuntimeException("Setting not found with id: " + id));
	}

	public SettingsEntity updateSetting(SettingsEntity settingsEntity)
	{
		SettingsEntity savedEntity = null;
		try
		{
			settingCacheManager.getAll();
			SettingsEntity existingEntity = settingCacheManager.getByPropertyName(settingsEntity.getPropertyName());
			if(existingEntity.isEncrypted())
			{
				existingEntity.setPropertyValue(EncryptionUtil.encrypt(settingsEntity.getPropertyValue()));
			}
			else
			{
				existingEntity.setPropertyValue(settingsEntity.getPropertyValue());
			}
			savedEntity = settingsRepository.save(existingEntity);
			settingCacheManager.update(savedEntity);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return savedEntity;
	}

}
