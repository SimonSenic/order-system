package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.dto.ProductDTO;
import com.example.demo.kafka.Producer;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.service.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/storage")
@AllArgsConstructor
public class StorageController {
	private StorageService storageService;
	private Producer producer;
	private ProductMapper productMapper;

	@GetMapping
	public ResponseEntity<List<ProductDTO>> getProducts(){
		List<ProductDTO> list = productMapper.toDTOs(storageService.findAll()); 
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable(value = "id") Long id){
		Product product = storageService.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
		ProductDTO productDTO = productMapper.toDTO(product);
		return ResponseEntity.ok(productDTO);
	}
	
	@PostMapping("/product")
	public ResponseEntity<ProductDTO> createProduct(@RequestHeader(value="Authorization") String header, @RequestBody ProductDTO body) {
		if (storageService.findByName(body.getName()).isPresent()) throw new IllegalStateException("Product already exists");
		Product product = new Product(body.getName(), body.getPrice());
		storageService.save(product);
		ProductDTO productDTO = productMapper.toDTO(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ProductDTO> order(@RequestHeader(value="Authorization") String header, 
			@PathVariable(value = "id") Long id, @RequestParam(required=false) Integer amount){
		Product product = storageService.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
		if(amount == null) amount = 1;
		else if(amount > product.getAvailability() || amount < 1) throw new IllegalStateException("Invalid amount");
		product.setAvailability(product.getAvailability() - amount);
		storageService.save(product);
		ProductDTO productDTO = productMapper.toDTO(product);
		try{ producer.sendMessage(productDTO, getUsername(header), amount); }
		catch(JsonProcessingException e) { e.printStackTrace(); }
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(productDTO);
	}
	
	 private String getUsername(String header){
	        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); //!!
	        String token = header.substring(7);
	        JWTVerifier verifier = JWT.require(algorithm).build();
	        DecodedJWT decodedJWT = verifier.verify(token);
	        return decodedJWT.getSubject();
	 }
}
