package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NonNull;

@Entity
@Table(name = "orders")
@Data
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "userId")
	@NonNull
	private Long userId;
	
	@Column(name = "productId")
	@NonNull
	private Long productId;
	
	@Column(name = "amount")
	@NonNull
	private Integer amount;
	
	@Column(name = "price")
	private Double price;
}
