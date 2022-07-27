package com.example.demo.dto;

import lombok.Data;

@Data
public class OrderDTO {
	private Long id;
	private Long userId;
	private Long productId;
	private int amount;
	private Double price;
}
