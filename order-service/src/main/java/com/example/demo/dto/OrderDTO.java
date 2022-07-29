package com.example.demo.dto;

import com.example.demo.model.State;

import lombok.Data;

@Data
public class OrderDTO {
	private Long id;
	private String username;
	private String product;
	private Integer amount;
	private Double price;
	private State state;
}