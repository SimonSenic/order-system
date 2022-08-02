package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.Product;
import com.example.demo.repository.StorageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StorageService {
	private StorageRepository storageRepository;
	
	public List<Product> findAll(){
		return storageRepository.findAll();
	}
	
	public Optional<Product> findById(Long id) {
		return storageRepository.findById(id);
	}
	
	public Product save(Product product) {
		return storageRepository.save(product);
	}
	
	public Optional<Product> findByName(String name){
		return storageRepository.findByName(name);
	}
	
	public String getUsername(String header){
        Algorithm algorithm = Algorithm.HMAC256("${secret.key}".getBytes()); 
        String token = header.substring(7);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
	}
}
