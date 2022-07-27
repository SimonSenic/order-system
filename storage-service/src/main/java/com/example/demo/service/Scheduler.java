package com.example.demo.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class Scheduler {
	StorageService storageService;
	
	@Scheduled(cron = "0 */5 * * * *") //5min
	public void checkAvailabilitySchedule() {
		List<Product> list = storageService.findAll();
		for(Product product : list) {
			if(product.getAvailability() < 2) {
				product.setAvailability(product.getAvailability() + 10);
				storageService.save(product);
			}
		}
	}
	
}
