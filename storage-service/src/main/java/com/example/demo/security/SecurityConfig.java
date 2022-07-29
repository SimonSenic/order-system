package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import static org.springframework.http.HttpMethod.PATCH;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers(GET, "/api/v1/storage/**").permitAll();
        http.authorizeRequests().antMatchers(POST, "/api/v1/storage/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(PATCH, "/api/v1/storage/**").hasAnyAuthority("CUSTOMER");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
