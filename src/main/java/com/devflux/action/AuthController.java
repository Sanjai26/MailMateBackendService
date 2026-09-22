package com.devflux.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devflux.entity.UserEntity;
import com.devflux.security.JWTUtils;

@RestController
@RequestMapping("/auth")
public class AuthController
{
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody UserEntity entity)
	{
		try
		{
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(entity.getUsername(), entity.getPassword()));
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			
			String token = jwtUtils.jwtGenerator(userDetails);
			return ResponseEntity.ok(Map.of("token",token));
		}
		catch (AuthenticationException e)
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Username and password"));
		}
	}

}
