package com.example.webshop.model.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemDto {

    private Long id;

    private String productName;

    private int quantity;

    private double price;


}
