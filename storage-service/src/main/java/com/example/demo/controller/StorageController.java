package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductDTO;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.service.Scheduler;
import com.example.demo.service.StorageService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/storage")
@AllArgsConstructor
public class StorageController {
	StorageService storageService;
	Scheduler scheduler;
	ProductMapper productMapper;

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
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO body) {
		if (storageService.findByName(body.getName()).isPresent()) throw new IllegalStateException("Product already exists");
		Product product = new Product(body.getName(), body.getPrice());
		storageService.save(product);
		ProductDTO productDTO = productMapper.toDTO(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable(value = "id") Long id, @RequestParam(required=false) Integer amount){
		Product product = storageService.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
		if(amount == null) amount = 1;
		else if(amount > product.getAvailability() || amount < 1) throw new IllegalStateException("Invalid amount");
		product.setAvailability(product.getAvailability() - amount);
		storageService.save(product);
		ProductDTO productDTO = productMapper.toDTO(product);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(productDTO);
	}
	
}
