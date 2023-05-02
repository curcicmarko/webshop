package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.CartDto;
import com.example.webshop.model.entity.Cart;

import java.util.stream.Collectors;

public class CartMapper {

    public static CartDto toDto(Cart cart){

        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setUserId(cart.getUser().getId());

        cartDto.setCartItemDtos(cart.getCartItems().stream()
                .map(CartItemMapper::toDto)
                .collect(Collectors.toList()));

        return cartDto;
    }

    public static Cart toEntity(CartDto cartDto){

        Cart cart = new Cart();
        cart.setId(cartDto.getId());
        cart.setTotalPrice(cartDto.getTotalPrice());

//        cart.setCartItems(cartDto.getCartItemDtos().stream()
//                .map(CartItemMapper::toEntity)
//                .collect(Collectors.toList()));


        return cart;
    }
}
