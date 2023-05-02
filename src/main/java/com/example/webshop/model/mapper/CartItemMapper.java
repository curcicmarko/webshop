package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.CartItemDto;
import com.example.webshop.model.entity.CartItem;

public class CartItemMapper {

        public static CartItemDto toDto(CartItem cartItem){

            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setId(cartItem.getId());
            cartItemDto.setProductName(cartItem.getProduct().getName());
            cartItemDto.setQuantity(cartItem.getQuantity());
            cartItemDto.setPrice(cartItem.getPrice());

            return cartItemDto;

        }

        public static CartItem toEntity(CartItemDto cartItemDto){

            CartItem cartItem = new CartItem();
            cartItem.setId(cartItemDto.getId());
            cartItem.setPrice(cartItemDto.getPrice());
            cartItem.setQuantity(cartItemDto.getQuantity());
            //cartItem.setProduct(ProductMapper.toEntity(cartItemDto.getProductDto()));
            //cartItem.setCart(CartMapper.toEntity(cartItemDto.getCartDto()));

            return cartItem;
        }

}
