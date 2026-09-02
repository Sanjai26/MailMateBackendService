package com.devflux.action;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devflux.entity.UserEntity;
import com.devflux.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController 
{
	private final UserService userService;

	public UserController(UserService userService)
	{
		this.userService = userService;
	}
	
	@GetMapping("/getAllUsers")
	public List<UserEntity> getAllUsers()
	{
		return userService.getAllUsers();
	}
	
}
