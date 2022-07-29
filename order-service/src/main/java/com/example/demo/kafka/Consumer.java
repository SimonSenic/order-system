package com.example.demo.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Consumer {
	@NonNull
	private ObjectMapper objectMapper;
	
	private final String productTopic = "${product.topic.name}";
	private final String userTopic = "${user.topic.name}";
	
	@KafkaListener(topics = { productTopic, userTopic })
	public void consumeMessage(String message, String username) throws JsonProcessingException{
		Product product = objectMapper.readValue(message, Product.class);
		
		System.out.println(message);
	}
}
