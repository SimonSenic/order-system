package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;

@Mapper(componentModel="spring")
public interface OrderMapper {
	OrderDTO toDTO(Order order);
	Order toOrder(OrderDTO orderDTO);
	List<OrderDTO> toDTOs(List<Order> orders);
}
