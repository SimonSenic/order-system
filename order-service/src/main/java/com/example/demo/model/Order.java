package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username")
	@NonNull
	private String username;
	
	@Column(name = "product")
	@NonNull
	private String product;
	
	@Column(name = "amount")
	@NonNull
	private Integer amount;
	
	@Column(name = "price")
	@NonNull
	private Double price;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	@NonNull
	private State state;
}
