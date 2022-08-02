package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;

@SpringBootTest
class OrderServiceApplicationTests {
	@Autowired
	private OrderService orderService;

	@Test
	void testFindAllOrders() {
		List<Order> list = orderService.findAll();
		assertNotNull(list); 
	}
	
	@Test
	void testFindOrder() {
		Long id = Long.valueOf(1);
		Order order = orderService.findById(id).get();
		assertNotNull(order); 
	}
	
	@Test
	void testFailGetUsernameByNullUsername() {
		assertThrows(NullPointerException.class, () -> {
			orderService.getUsername(null);
		});
	}
	
	@Test
	void testFailGetUsernameByEmptyUsername() {
		assertThrows(StringIndexOutOfBoundsException.class, () -> {
			orderService.getUsername("");
		});
	}
	
	@Test
	void testFailGetUsernameByWrongUsername() {
		assertThrows(JWTDecodeException.class, () -> {
			orderService.getUsername("wrong_username");
		});
	}

}
