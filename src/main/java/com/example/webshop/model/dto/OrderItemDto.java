package com.example.webshop.model.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDto {

    private Long id;

    private int quantity;

    private double price;

    private String productName;

    private Long orderId;


}
