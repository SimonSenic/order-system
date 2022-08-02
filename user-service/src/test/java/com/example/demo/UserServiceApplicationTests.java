package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@SpringBootTest
class UserServiceApplicationTests {
	@Autowired
	private UserService userService;
	
	@Test
	void testFindAllUsers() {
		List<User> list = userService.findAll();
		assertNotNull(list); 
	}
	
	@Test
	void testFindUser() {
		Long id = Long.valueOf(1);
		User user = userService.findById(id).get();
		assertNotNull(user); 
	}
	
	@Test
	void testFailWhenTryFindUserByNullName() {
		assertThrows(NoSuchElementException.class, () -> {
			userService.findByName(null).get();
		});
	}
	
	@Test
	void testFailWhenTryFindUserByEmptyName() {
		assertThrows(NoSuchElementException.class, () -> {
			userService.findByName("").get();
		});
	}
	
	@Test
	void testFailWhenTryFindUserByWrongName() {
		assertThrows(NoSuchElementException.class, () -> {
			userService.findByName("wrong_name").get();
		});
	}
	
	@Test
	void testFailWhenTryFindUserByNullEmail() {
		assertThrows(NoSuchElementException.class, () -> {
			userService.findByEmail(null).get();
		});
	}
	
	@Test
	void testFailWhenTryFindUserByEmptyEmail() {
		assertThrows(NoSuchElementException.class, () -> {
			userService.findByEmail("").get();
		});
	}
	
	@Test
	void testFailWhenTryFindUserByWrongEmail() {
		assertThrows(NoSuchElementException.class, () -> {
			userService.findByEmail("wrong_email").get();
		});
	}
	
	@Test
	void testFailLoadUserByUserNameMethodByNullUsername() {
		assertThrows(UsernameNotFoundException.class, () -> {
			userService.loadUserByUsername(null);
		});
	}
	
	@Test
	void testFailLoadUserByUserNameMethodByEmptyUsername() {
		assertThrows(UsernameNotFoundException.class, () -> {
			userService.loadUserByUsername("");
		});
	}
	
	@Test
	void testFailLoadUserByUserNameMethodByWrongUsername() {
		assertThrows(UsernameNotFoundException.class, () -> {
			userService.loadUserByUsername("wrong_username");
		});
	}
	
}
