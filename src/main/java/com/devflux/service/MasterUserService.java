package com.devflux.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devflux.entity.MasterUserEntity;
import com.devflux.repository.MasterUserRepository;

@Service
public class MasterUserService
{
	private final MasterUserRepository masterUserRepository;

	public MasterUserService(MasterUserRepository masterUserRepository)
	{
		this.masterUserRepository = masterUserRepository;
	}

	public MasterUserEntity addMasterUser(String username, String email, String password, String lastUpdatedAt)
	{
		MasterUserEntity masterUser = new MasterUserEntity();
		masterUser.setUsername(username);
		masterUser.setEmail(email);
		masterUser.setPassword(password);
		masterUser.setLastUpdatedAt(lastUpdatedAt);
		return masterUserRepository.save(masterUser);
	}

	public List<MasterUserEntity> getAllMasterUsers()
	{
		return masterUserRepository.findAll();
	}

	public MasterUserEntity getMasterUserById(long id)
	{
		return masterUserRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Master user not found with id: " + id));
	}

	public MasterUserEntity modifyMasterUser(long id, String username, String email, String password, String lastUpdatedAt)
	{
		MasterUserEntity masterUser = getMasterUserById(id);
		masterUser.setUsername(username);
		masterUser.setEmail(email);
		masterUser.setPassword(password);
		masterUser.setLastUpdatedAt(lastUpdatedAt);
		return masterUserRepository.save(masterUser);
	}

	public void deleteMasterUserById(long id)
	{
		if (!masterUserRepository.existsById(id))
		{
			throw new RuntimeException("Master user not found with id: " + id);
		}
		masterUserRepository.deleteById(id);
	}
}
