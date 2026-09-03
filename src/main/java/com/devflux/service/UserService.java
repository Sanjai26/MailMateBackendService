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
		userEntity.setFirstName(firstName);
		userEntity.setLastName(lastName);
		userEntity.setMailAddress(mailAddress);
		userEntity.setPhoneNumber(phoneNumber);
		userEntity.setCountry(country);
		userEntity.setCreatedAt(LocalDateTime.now());
		userEntity.setActive(isActive);
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

	public List<UserEntity> findByCountry(String country)
	{
		return userRepository.findByCountry(country);
	}

	public UserEntity modifyUser(long id, String firstName, String lastName, String mailAddress, String phoneNumber,
			String country, boolean isActive)
	{
		UserEntity userEntity = getUserById(id);
		userEntity.setFirstName(firstName);
		userEntity.setLastName(lastName);
		userEntity.setMailAddress(mailAddress);
		userEntity.setPhoneNumber(phoneNumber);
		userEntity.setCountry(country);
		userEntity.setActive(isActive);
		return userRepository.save(userEntity);
	}

	public void deleteUserById(long id)
	{
		if (!userRepository.existsById(id))
		{
			throw new RuntimeException("User not found with id: " + id);
		}
		userRepository.deleteById(id);
	}
}
