package com.example.demo.dto;

import com.example.demo.model.Role;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	private String name;
	private String password;
	private String email;
	private String fullName;
	private String address;
	private Role role;
}
