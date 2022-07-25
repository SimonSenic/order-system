package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

@Mapper(componentModel="spring")
public interface UserMapper {
	UserDTO toDTO(User user);
	User toUser(UserDTO userDTO);
	List<UserDTO> toDTOs(List<User> users);
}
