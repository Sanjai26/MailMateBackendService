package com.devflux.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.devflux.security.JWTFilter;
import com.devflux.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig
{
	@Autowired
	private JWTFilter filter;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    http
	        .authorizeHttpRequests(authz -> authz
	        		
        		// User registration does not require authentication
	            .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

	            // User and client APIs require authentication
	            .requestMatchers("/api/users/**", "/api/clients/**").authenticated()

	            // Root URL does not require authentication
	            .requestMatchers("/").permitAll()

	            // All other requests are allowed
	            .anyRequest().permitAll()
	        )

	        // Enable form login
	      //  .formLogin(form -> form.permitAll().defaultSuccessUrl("/dashboard"))
	        
	        //Disabling CSRF
	        .csrf(csrf -> csrf.disable())
	        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
	
//	@Bean
//	public UserDetailsService inMemoryUserCreator(PasswordEncoder encoder)
//	{
//		UserDetails user = User.withUsername("user").password(encoder.encode("user1234")).roles("USER").build();
//		
//		UserDetails admin = User.withUsername("admin").password(encoder.encode("admin1234")).roles("ADMIN").build();
//		
//		return new InMemoryUserDetailsManager(user, admin);
//		
//		return new CustomUserDetailsService();
//	}
	
	@Bean
	public UserDetailsService userDetailService()
	{
		return new CustomUserDetailsService();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authenticationProvider =  new DaoAuthenticationProvider(userDetailService());
		authenticationProvider.setPasswordEncoder(getPasswordEncoder());
		
		return authenticationProvider;
	}
	
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean 
	public AuthenticationManager authenticationManager()
	{
		return new ProviderManager(List.of(authenticationProvider()));
	}
}
