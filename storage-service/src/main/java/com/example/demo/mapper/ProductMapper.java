package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;

@Mapper(componentModel="spring")
public interface ProductMapper {
	ProductDTO toDTO(Product product);
	Product toProduct(ProductDTO productDTO);
	List<ProductDTO> toDTOs(List<Product> products);
}
