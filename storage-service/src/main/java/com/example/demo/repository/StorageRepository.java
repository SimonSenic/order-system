package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Product;

public interface StorageRepository extends JpaRepository<Product, Long>{
	Optional<Product> findByName(String name);
}
