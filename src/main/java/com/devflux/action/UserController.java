package com.devflux.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devflux.entity.UserEntity;
import com.devflux.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController
{
	@Autowired
	private UserService userService;

	@Autowired
	private  PasswordEncoder encoder;

	@GetMapping("/{id}")
	public UserEntity getUserById(@PathVariable long id)
	{
		return userService.getUserById(id);
	}

	@PostMapping
	public UserEntity createUser(@RequestBody UserEntity user)
	{
		user.setPassword(encoder.encode(user.getPassword()));
		return userService.createUser(user);
	}
}
