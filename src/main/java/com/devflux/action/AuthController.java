package com.devflux.action;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devflux.entity.UserEntity;
import com.devflux.security.JWTUtils;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
	private	final AuthenticationManager	authenticationManager;

	private				final JWTUtils				jwtUtils;

	AuthController(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserEntity entity, HttpServletResponse response)
	{
		try
		{
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(entity.getUsername(), entity.getPassword()));
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			String token = jwtUtils.jwtGenerator(userDetails);
			
			ResponseCookie cookie = ResponseCookie.from("jwt", token)
					.httpOnly(true)
					.secure(false)
					.path("/")
					.maxAge(60 * 60)
					.sameSite("Lax")
					.build();

				return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(Map.of("username", entity.getUsername(), "token", token));
		}
		catch (AuthenticationException e)
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Username and password"));
		}
	}

	@GetMapping("/me")
	public ResponseEntity<?> me(Authentication authentication)
	{
		if (authentication == null)
		{
			return ResponseEntity.status(401).build();
		}
		return ResponseEntity.ok(Map.of("username", authentication.getName()));
	}

}
