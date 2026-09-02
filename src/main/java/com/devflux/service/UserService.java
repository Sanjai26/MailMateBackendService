package com.devflux.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devflux.entity.UserEntity;
import com.devflux.repository.UserRepository;

@Service
public class UserService 
{
	@Autowired
	private UserRepository userRepository;
	
	public List<UserEntity> getAllUsers()
	{
		List<UserEntity> getAllUserEntity = userRepository.findAll();
		return getAllUserEntity;
	}

}
