package com.example.demo.dto;

import com.example.demo.model.Role;

import lombok.Data;

@Data
public class UserDTO {
	private String name;
	private String password;
	private String email;
	private String address;
	private Role role;
}