package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<List<OrderDTO>> getOrders(@RequestHeader(value="Authorization") String header){
		List<OrderDTO> list = orderMapper.toDTOs(orderService.findAll()); 
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getOrder(@RequestHeader(value="Authorization") String header, @PathVariable(value = "id") Long id){
		Order order = orderService.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
		OrderDTO orderDTO = orderMapper.toDTO(order); 
		return ResponseEntity.ok(orderDTO);
	}
	
	@GetMapping("/my")
	public ResponseEntity<List<OrderDTO>> getUserOrders(@RequestHeader(value="Authorization") String header){
		List<Order> list = orderService.findAll().stream().filter(temp -> temp.getUsername().equals(orderService.getUsername(header)))
				.collect(Collectors.toList());
		List<OrderDTO> listDTO = orderMapper.toDTOs(list); 
		return ResponseEntity.ok(listDTO);
	}
}
