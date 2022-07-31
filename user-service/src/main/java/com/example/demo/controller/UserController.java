package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.security.PasswordEncoder;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserController {
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	private UserMapper userMapper;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers(@RequestHeader(value="Authorization") String header){
		List<UserDTO> list = userMapper.toDTOs(userService.findAll()); 
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@RequestHeader(value="Authorization") String header, @PathVariable(value = "id") Long id){
		User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		UserDTO userDTO = userMapper.toDTO(user);
		System.out.println(user.toString());
		return ResponseEntity.ok(userDTO);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@RequestBody UserDTO body){
		User user = userService.findByName(body.getName()).orElseThrow(() -> new IllegalArgumentException("User not found"));
		UserDTO userDTO = userMapper.toDTO(user);
		return ResponseEntity.ok(userDTO);
	}
	
	@PostMapping("/customer")
	public ResponseEntity<UserDTO> createCustomer(@RequestHeader(value="Authorization") String header, @RequestBody UserDTO body) {
		if (userService.findByName(body.getName()).isPresent()) throw new IllegalStateException("Name is occupied");
		else if(userService.findByEmail(body.getEmail()).isPresent()) throw new IllegalStateException("Email is occupied");
		User user = new User(body.getName(), body.getEmail(), Role.CUSTOMER);
		userService.save(user);
		log.info("new customer created (profile update required -> localhost:8080/api/v1/users/customer/{id})");
		UserDTO userDTO = userMapper.toDTO(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
	}

	@PatchMapping("/customer/{id}")
	public ResponseEntity<UserDTO> updateCustomer(@PathVariable(value = "id") Long id, @RequestBody UserDTO body) {
		User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		if (userService.findByName(body.getName()).isPresent()) throw new IllegalStateException("Name is occupied");
		else if(userService.findByEmail(body.getEmail()).isPresent()) throw new IllegalStateException("Email is occupied");
		else if(user.getPassword()!=null || user.getFullName()!=null || user.getAddress()!=null) throw new IllegalStateException("Profile already updated");
		user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(body.getPassword()));
		user.setFullName(body.getFullName());
		user.setAddress(body.getAddress());
		userService.save(user);
		log.info("profile updated (login enabled)");
		UserDTO userDTO = userMapper.toDTO(user);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDTO);
	}
	
	@PostMapping("/admin")
	public ResponseEntity<UserDTO> createAdmin(@RequestHeader(value="Authorization") String header, @RequestBody UserDTO body) {
		if (userService.findByName(body.getName()).isPresent()) throw new IllegalStateException("Name is occupied");
		else if(userService.findByEmail(body.getEmail()).isPresent()) throw new IllegalStateException("Email is occupied");
		User user = new User(body.getName(), body.getEmail(), Role.ADMIN);
		userService.save(user);
		log.info("new admin created (password update required -> localhost:8080/api/v1/users/admin/{id})");
		UserDTO userDTO = userMapper.toDTO(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
	}
	
	@PatchMapping("/admin/{id}")
	public ResponseEntity<UserDTO> updateAdmin(@PathVariable(value = "id") Long id, @RequestBody UserDTO body) {
		User user = userService.findById(id).filter(temp -> temp.getRole().equals(Role.ADMIN))
				.orElseThrow(() -> new IllegalArgumentException("Not an admin"));
		if (userService.findByName(body.getName()).isPresent()) throw new IllegalStateException("Name is occupied");
		else if(userService.findByEmail(body.getEmail()).isPresent()) throw new IllegalStateException("Email is occupied");
		else if(user.getPassword()!=null) throw new IllegalStateException("Password already updated");
		user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(body.getPassword()));
		userService.save(user);
		log.info("password updated (login enabled)");
		UserDTO userDTO = userMapper.toDTO(user);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDTO);
	}
}
