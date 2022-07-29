package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
}
