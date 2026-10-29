package com.devflux.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.devflux.security.JWTFilter;
import com.devflux.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig
{
	private final JWTFilter filter;

	SecurityConfig(JWTFilter filter) {
		this.filter = filter;
	}
	// @Bean
	// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	// Exception {
	//
	// http
	// .authorizeHttpRequests(authz -> authz
	//
	// // User registration does not require authentication
	// .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
	//
	// // User and Customer APIs require authentication
	// .requestMatchers("/api/users/**", "/api/customers/**").authenticated()
	//
	// // Root URL does not require authentication
	// .requestMatchers("/").permitAll()
	//
	// // All other requests are allowed
	// .anyRequest().permitAll()
	// )
	//
	// // Enable form login
	// // .formLogin(form -> form.permitAll().defaultSuccessUrl("/dashboard"))
	//
	// //Disabling CSRF
	// .csrf(csrf -> csrf.disable())
	// .sessionManagement(sess ->
	// sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	// .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	//
	// return http.build();
	// }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{

		http
				// CORS
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))

				// JWT API - CSRF disabled
				.csrf(csrf -> csrf.disable())

				// No form login
				.formLogin(form -> form.disable())

				// No HTTP Basic
				.httpBasic(basic -> basic.disable())

				// Stateless JWT authentication
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Authorization
				.authorizeHttpRequests(authz -> authz

						// Login does not require JWT
						.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

						// Registration does not require JWT
						.requestMatchers(HttpMethod.POST, "/api/users").permitAll()

						// /me DOES require JWT
						.requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()

						// Protected APIs
						.requestMatchers("/api/users/**", "/api/customers/**").authenticated()

						// Everything else
						.anyRequest().permitAll())

				// JWT filter
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// @Bean
	// public UserDetailsService inMemoryUserCreator(PasswordEncoder encoder)
	// {
	// UserDetails user =
	// User.withUsername("user").password(encoder.encode("user1234")).roles("USER").build();
	//
	// UserDetails admin =
	// User.withUsername("admin").password(encoder.encode("admin1234")).roles("ADMIN").build();
	//
	// return new InMemoryUserDetailsManager(user, admin);
	//
	// return new CustomUserDetailsService();
	// }

	@Bean
	public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService)
	{
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(customUserDetailsService);
		authenticationProvider.setPasswordEncoder(getPasswordEncoder());

		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(DaoAuthenticationProvider authenticationProvider)
	{
		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource()
	{

		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(List.of("http://localhost:5173"));

		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
