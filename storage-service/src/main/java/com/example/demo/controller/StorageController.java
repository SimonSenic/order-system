package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductDTO;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/storage")
@AllArgsConstructor
public class StorageController {
	ProductRepository productRepository;
	ProductMapper productMapper;

	@GetMapping
	public ResponseEntity<List<ProductDTO>> getProducts(){
		List<ProductDTO> list = productMapper.toDTOs(productRepository.findAll()); 
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable(value = "id") Long id){
		Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
		ProductDTO productDTO = productMapper.toDTO(product);
		return ResponseEntity.ok(productDTO);
	}
}
