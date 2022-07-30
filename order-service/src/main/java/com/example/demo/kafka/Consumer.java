package com.example.demo.kafka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Order;
import com.example.demo.model.State;
import com.example.demo.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Consumer {
	@NonNull
	private OrderService orderService;
	@NonNull
	private ObjectMapper objectMapper;
	
	private final String orderTopic = "${order.topic.name}";
	private final String paymentTopic = "${payment.topic.name}";
	
	@KafkaListener(topics = orderTopic)
	public void consumeOrder(String message) throws JsonProcessingException, IOException {
		BufferedReader reader = new BufferedReader(new StringReader(message));
		ProductDTO product = objectMapper.readValue(reader.readLine(), ProductDTO.class);
		String username = reader.readLine();
		Integer amount = Integer.valueOf(reader.readLine());
		Order order = new Order(username, product.getName(), amount, product.getPrice()*amount, State.WAITING_FOR_PAYMENT);
		orderService.save(order);
	}
	
	@KafkaListener(topics = paymentTopic)
	public void consumePayment(String message) throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(message));
		Long id = Long.valueOf(reader.readLine());
		String username = reader.readLine();
		Order order = orderService.findById(id).filter(temp -> temp.getUsername().equals(username) && temp.getState().equals(State.WAITING_FOR_PAYMENT))
				.orElseThrow(() -> new IllegalArgumentException("Order not found"));
		order.setState(State.SENT);
		orderService.save(order);
	}
}
