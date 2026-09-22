package com.devflux.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devflux.entity.UserEntity;
import com.devflux.repository.UserRepository;

@Service
public class UserService
{
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	public UserEntity addUser(String firstName, String lastName, String mailAddress, String phoneNumber, String country,
			boolean isActive)
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
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	public UserEntity createUser(UserEntity user)
	{
		user.setLastUpdatedAt(LocalDateTime.now().toString());
		return userRepository.save(user);
	}
}
