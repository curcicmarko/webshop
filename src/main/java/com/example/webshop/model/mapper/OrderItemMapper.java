package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.OrderItemDto;
import com.example.webshop.model.entity.OrderItem;

public class OrderItemMapper {

    public static OrderItemDto toDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .productName(orderItem.getProduct().getName())
                .orderId(orderItem.getOrder().getId())
                .build();
    }


    public static OrderItem toEntity(OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .price(orderItemDto.getPrice())
                .quantity(orderItemDto.getQuantity())
                .build();
    }

}
