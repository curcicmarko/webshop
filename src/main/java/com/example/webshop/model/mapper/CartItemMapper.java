package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.CartItemDto;
import com.example.webshop.model.entity.CartItem;

public class CartItemMapper {


    public static CartItemDto toDto(CartItem cartItem) {
        return CartItemDto.builder()
                .id(cartItem.getId())
                .productName(cartItem.getProduct().getName())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .build();
    }

    public static CartItem toEntity(CartItemDto cartItemDto) {
        return CartItem.builder()
                .id(cartItemDto.getId())
                .price(cartItemDto.getPrice())
                .quantity(cartItemDto.getQuantity())
                .build();
    }


}
