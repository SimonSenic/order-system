package com.example.demo.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Producer {
	@NonNull
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@NonNull
	private ObjectMapper objectMapper;
	
	@Value("${product.topic.name}")
	private String productTopic;
	
	@Value("${user.topic.name}")
	private String userTopic;
	
	public void sendMessage(Product product, String username) throws JsonProcessingException{
		//System.out.println("message sent: " +objectMapper.writeValueAsString(product));
		kafkaTemplate.send(productTopic, objectMapper.writeValueAsString(product));
		kafkaTemplate.send(userTopic, objectMapper.writeValueAsString(username));
	}
}
