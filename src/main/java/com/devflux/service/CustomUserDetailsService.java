package com.devflux.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.devflux.entity.UserEntity;
import com.devflux.repository.UserRepository;

@Component
public class CustomUserDetailsService  implements UserDetailsService
{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		UserEntity userEntity =  userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
	}
}
