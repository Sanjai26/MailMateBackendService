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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.devflux.security.JWTFilter;
import com.devflux.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig
{
	@Autowired
	private JWTFilter filter;
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
	// // User and client APIs require authentication
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
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()))

				.csrf(csrf -> csrf.disable())

				.formLogin(form -> form.disable())

				.httpBasic(basic -> basic.disable())

				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// Disable Spring Security's default login page
				.formLogin(form -> form.disable())

				// Disable HTTP Basic authentication
				.httpBasic(basic -> basic.disable())

				// Disable CSRF for stateless JWT API
				.csrf(csrf -> csrf.disable())

				// JWT authentication should be stateless
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(authz -> authz

						// Login API - no JWT required
						.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

						// User registration - no JWT required
						.requestMatchers(HttpMethod.POST, "/api/users").permitAll()

						// Authentication check - no?
						// IMPORTANT: if /me validates JWT, don't permit it.
						.requestMatchers("/api/auth/me").authenticated()

						// Protected APIs
						.requestMatchers("/api/users/**", "/api/customers/**").authenticated()

						// Root URL
						.requestMatchers("/").permitAll()

						// Everything else
						.anyRequest().permitAll())

				// Your JWT filter
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
	public UserDetailsService userDetailService()
	{
		return new CustomUserDetailsService();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailService());
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

	@Bean
	public CorsConfigurationSource corsConfigurationSource()
	{

		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(List.of("http://localhost:5173"));

		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
