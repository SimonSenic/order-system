package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderDTO;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
	private OrderService orderService;
	private OrderMapper orderMapper;
	
	@GetMapping
	public ResponseEntity<List<OrderDTO>> getOrders(){
		List<OrderDTO> list = orderMapper.toDTOs(orderService.findAll()); 
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getOrder(@PathVariable(value = "id") Long id){
		Order order = orderService.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
		OrderDTO orderDTO = orderMapper.toDTO(order);
		return ResponseEntity.ok(orderDTO);
	}
	
	@PostMapping
	public ResponseEntity<OrderDTO> order(@RequestHeader(value="Authorization") String header){
		System.out.println("ide");
		//updateProduct - ziskam produkt, getnem Usera, a jak bude payment accepted mozem dat order a hotovo
		//Order order = new Order();
		//orderRepository.save(order);
		//OrderDTO orderDTO = orderMapper.toDTO(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
}
