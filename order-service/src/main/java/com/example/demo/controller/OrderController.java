package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderDTO;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
	OrderRepository orderRepository;
	OrderMapper orderMapper;
	
	@GetMapping
	public ResponseEntity<List<OrderDTO>> getOrders(){
		List<OrderDTO> list = orderMapper.toDTOs(orderRepository.findAll()); 
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getOrder(@PathVariable(value = "id") Long id){
		Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
		OrderDTO orderDTO = orderMapper.toDTO(order);
		return ResponseEntity.ok(orderDTO);
	}
}
