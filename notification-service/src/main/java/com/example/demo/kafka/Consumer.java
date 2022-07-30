package com.example.demo.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@NoArgsConstructor
@Slf4j
public class Consumer {
	private final String logTopic = "${log.topic.name}";
	
	@KafkaListener(topics = logTopic)
	public void consumeLog(String message) {
		log.info(message);
	}
}
