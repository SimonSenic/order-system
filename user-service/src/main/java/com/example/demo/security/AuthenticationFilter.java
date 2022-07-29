package com.example.demo.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		super("/api/v1/users/login");
	    this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String body = null;
	    try { body = request.getReader().lines().collect(Collectors.joining()); }
	    catch (Exception e) { e.printStackTrace(); }

	    JSONObject jsonObject = new JSONObject(body);
	    String username = jsonObject.getString("name");
	    String password = jsonObject.getString("password");
	    UsernamePasswordAuthenticationToken authenticationToken =
	    	new UsernamePasswordAuthenticationToken(username, password);

	    return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	        FilterChain chain, Authentication authResult) throws IOException, ServletException {
	    User user = (User) authResult.getPrincipal();
	    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); //!!
	    String token = JWT.create().withSubject(user.getUsername())
	    		.withExpiresAt(new Date(System.currentTimeMillis() +3240000))
	            .withIssuer(request.getRequestURL().toString())
	            .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
	            .sign(algorithm);
	    Map<String, String> map = new HashMap<>();
	    map.put("token", token);
	    response.setContentType("application/json");
	    new ObjectMapper().writeValue(response.getOutputStream(), map);
	}
	 
}
