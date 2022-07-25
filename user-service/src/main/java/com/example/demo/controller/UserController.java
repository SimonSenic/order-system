package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
	private UserRepository userRepository;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers(){
		List<UserDTO> list = UserMapper.INSTANCE.toDTOs(userRepository.findAll()); 
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable(value = "id") Long id){
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		UserDTO userDTO = UserMapper.INSTANCE.toDTO(user);
		return ResponseEntity.ok(userDTO);
	}
	
	@PostMapping("/customer")
	public ResponseEntity<UserDTO> createCustomer(@RequestBody UserDTO body) {
		if (userRepository.findByEmail(body.getEmail()).isPresent())
            throw new IllegalStateException("User with such email already exists");
		User user = new User(body.getEmail());
		user.setRole(Role.CUSTOMER);
		//userRepository.save(user);
		UserDTO userDTO = UserMapper.INSTANCE.toDTO(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
	}

	@PatchMapping("/customer/{id}")
	public ResponseEntity<UserDTO> updateCustomer(@PathVariable(value = "id") Long id, @RequestBody UserDTO body) {
		 if (userRepository.findByName(body.getName()).isPresent())
	            throw new IllegalStateException("User with such name already exists");
		 User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		 user.setName(body.getName());
		 user.setPassword(body.getPassword());
		 user.setAddress(body.getAddress());
		 userRepository.save(user);
		 UserDTO userDTO = UserMapper.INSTANCE.toDTO(user);
		 return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDTO);
	}
	
}
