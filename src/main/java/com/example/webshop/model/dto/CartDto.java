package com.example.webshop.model.dto;

import java.util.List;

public class CartDto {

    private Long id;
    private List<CartItemDto> cartItemDtos;
    private double totalPrice;

    private Long userId;

    public CartDto() {
    }

    public CartDto(Long id,Long userId, List<CartItemDto> cartItemDtos, double totalPrice) {
        this.id = id;
        this.cartItemDtos = cartItemDtos;
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItemDto> getCartItemDtos() {
        return cartItemDtos;
    }

    public void setCartItemDtos(List<CartItemDto> cartItemDtos) {
        this.cartItemDtos = cartItemDtos;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
