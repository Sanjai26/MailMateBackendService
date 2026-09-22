package com.devflux.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils
{
	private static final String SCERETE_KEY_STRING ="dqyvSK83txJubSFfYNhXJAASfwHEHLqE";
	private static final SecretKey SCERETE_KEY =Keys.hmacShaKeyFor(SCERETE_KEY_STRING.getBytes());
	public String jwtGenerator( UserDetails userDetails)
	{
		return  Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + (1000*60*60)))
				.signWith(SCERETE_KEY, Jwts.SIG.HS256)
				.compact();
	}
	
	public boolean validateToken(String token, UserDetails userDetails)
	{
		return extractUserName(token).equalsIgnoreCase(userDetails.getUsername());
	}
	
	public String extractUserName(String token)
	{
		Claims payload = Jwts.parser().verifyWith(SCERETE_KEY).build().parseSignedClaims(token).getPayload();
		String subject = payload.getSubject();
		// === > here we are getting our need details from token using the token, which how we are prepare.
//		Date issuedAt= payload.getIssuedAt();
//		Date expiredAt= payload.getExpiration();
		
		return subject;
	}
}
