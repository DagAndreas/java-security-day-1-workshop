package com.booleanuk.library.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	// added bean to be used by others
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{ // looks like a stream, were gonna chain one thing into another in terms of accesspoints
		httpSecurity
				// already authenticated
				.authorizeHttpRequests((requests) -> requests // request will be returned if
						.requestMatchers("/books", "/books/*") // old, deprictaed = antMatches
						.authenticated()
				)
				// or
				.formLogin((form) -> form
						.loginPage("/login")
						.permitAll()
				)

				// or logout
				.logout((logout) -> logout.permitAll());

		// now generated filter chain config. Build and return it
		return httpSecurity.build();
	}

	@Bean
	public UserDetailsService userDetailsService(){
		// hardcoding a user
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("asdasd")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}

}
