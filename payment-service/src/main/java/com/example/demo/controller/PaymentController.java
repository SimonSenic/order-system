package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.kafka.Producer;
import com.example.demo.model.Data;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/payment")
@AllArgsConstructor
@Slf4j
public class PaymentController {
	Producer producer;
	
	@PostMapping("/{id}")
	public ResponseEntity<Map<String, String>> pay(@RequestHeader(value="Authorization") String header, 
			@PathVariable(value = "id") Long id, @RequestBody Data body) {
		try{ 
			Thread.sleep(5000);
			producer.sendMessage(id, getUsername(header)); 
		}catch(JsonProcessingException | InterruptedException e) { 
			log.error(e.getMessage());
		}
		log.info("payment successful");
		Map<String, String> response = new HashMap<>();
		response.put("status", "payment successful");
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	private String getUsername(String header){
        Algorithm algorithm = Algorithm.HMAC256("${secret.key}".getBytes());
        String token = header.substring(7);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
	}
}
