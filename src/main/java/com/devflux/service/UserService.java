package com.devflux.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.devflux.entity.UserEntity;
import com.devflux.hazelcast.cachemanager.UserCacheManager;
import com.devflux.repository.UserRepository;

@Service
public class UserService
{
	private final UserRepository	userRepository;

	private final UserCacheManager	userCacheManager;

	public UserService(UserRepository userRepository, UserCacheManager userCacheManager)
	{
		this.userRepository = userRepository;
		this.userCacheManager = userCacheManager;
	}

	public UserEntity addUser(String firstName, String lastName, String mailAddress, String phoneNumber, String country, boolean isActive)
	{
		UserEntity userEntity = new UserEntity();
		return userRepository.save(userEntity);
	}

	public List<UserEntity> getAllUsers()
	{
		return userRepository.findAll();
	}

	public UserEntity getUserById(long id)
	{
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	public UserEntity createUser(UserEntity user)
	{
		user.setLastUpdatedAt(LocalDateTime.now().toString());
		return userRepository.save(user);
	}

	public UserEntity getUserIdFromAuthentication()
	{
		// 1. Get the authenticated user from Spring Security
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal()))
		{
			throw new RuntimeException("User is not authenticated");
		}

		// 2. Get the username extracted from the JWT
		String username = authentication.getName();

		// 3. Fetch the actual user entity from the database
		UserEntity createdBy = userCacheManager.getUserEntitiyByUserName(username);

		return createdBy;

	}

}
