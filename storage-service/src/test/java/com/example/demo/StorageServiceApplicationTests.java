package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.demo.model.Product;
import com.example.demo.service.StorageService;

@SpringBootTest
class StorageServiceApplicationTests {
	@Autowired
	private StorageService storageService;

	@Test
	void testFindAllProducts() {
		List<Product> list = storageService.findAll();
		assertNotNull(list); 
	}
	
	@Test
	void testFindProduct() {
		Long id = Long.valueOf(1);
		Product product = storageService.findById(id).get();
		assertNotNull(product); 
	}
	
	@Test
	void testFailWhenTryFindProductByNullName() {
		assertThrows(NoSuchElementException.class, () -> {
			storageService.findByName(null).get();
		});
	}
	
	@Test
	void testFailWhenTryFindProductByEmptyName() {
		assertThrows(NoSuchElementException.class, () -> {
			storageService.findByName("").get();
		});
	}
	
	@Test
	void testFailWhenTryFindProductByWrongName() {
		assertThrows(NoSuchElementException.class, () -> {
			storageService.findByName("wrong_name").get();
		});
	}
	
	@Test
	void testFailGetUsernameByNullUsername() {
		assertThrows(NullPointerException.class, () -> {
			storageService.getUsername(null);
		});
	}
	
	@Test
	void testFailGetUsernameByEmptyUsername() {
		assertThrows(StringIndexOutOfBoundsException.class, () -> {
			storageService.getUsername("");
		});
	}
	
	@Test
	void testFailGetUsernameByWrongUsername() {
		assertThrows(JWTDecodeException.class, () -> {
			storageService.getUsername("wrong_username");
		});
	}
}
