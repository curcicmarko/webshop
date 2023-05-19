package com.example.webshop.model.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDto {

    private Long id;

    private List<CartItemDto> cartItemDtos;

    private double totalPrice;

    private Long userId;


}
