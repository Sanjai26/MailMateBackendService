package com.devflux.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.devflux.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter
{
	@Autowired 
	private JWTUtils jwtUtils;
	
	@Autowired 
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
	{
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer "))
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = header.substring(7);
		
		try
		{
			String userName = jwtUtils.extractUserName(token);
			if(userName !=null && SecurityContextHolder.getContext().getAuthentication() == null)
			{
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
				if(jwtUtils.validateToken(token, userDetails))
				{
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}
		catch (Exception e)
		{
			Map<String, String> errorMap = new  HashMap<String, String>();
			errorMap.put("error", "Invalid Token");
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(errorMap);
			
			response.getWriter().write(json);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		filterChain.doFilter(request, response);
	}

}
