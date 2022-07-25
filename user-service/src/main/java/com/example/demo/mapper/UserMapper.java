package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
	
	UserDTO toDTO(User user);
	User toUser(UserDTO userDTO);
	List<UserDTO> toDTOs(List<User> users);
}