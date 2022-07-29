package com.example.demo.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Producer {
	@NonNull
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${payment.topic.name}")
	private String paymentTopic;
	
	public void sendMessage(Long id, String username) throws JsonProcessingException{
		StringBuilder sb = new StringBuilder();
		sb.append(id +"\n");
		sb.append(username);
		kafkaTemplate.send(paymentTopic, sb.toString());
	}
}
