package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.CartDto;
import com.example.webshop.model.entity.Cart;

import java.util.stream.Collectors;

public class CartMapper {


    public static CartDto toDto(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .totalPrice(cart.getTotalPrice())
                .userId(cart.getUser().getId())
                .cartItemDtos(cart.getCartItems().stream()
                        .map(CartItemMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }


    public static Cart toEntity(CartDto cartDto) {
        return Cart.builder()
                .id(cartDto.getId())
                .totalPrice(cartDto.getTotalPrice())
                .build();
    }

}
