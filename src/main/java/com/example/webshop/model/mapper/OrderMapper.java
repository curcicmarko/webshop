package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.OrderDto;
import com.example.webshop.model.entity.Order;

import java.util.stream.Collectors;

public class OrderMapper {


    public static OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderPrice(order.getOrderPrice())
                .createdAt(order.getCreatedAt())
                .orderItemDtos(order.getOrderItems().stream()
                        .map(OrderItemMapper::toDto)
                        .collect(Collectors.toList()))
                .build();

    }


    public static Order toEntity(OrderDto orderDto) {
        return Order.builder()
                .orderPrice(orderDto.getOrderPrice())
                .createdAt(orderDto.getCreatedAt())
                .orderItems(orderDto.getOrderItemDtos().stream()
                        .map(OrderItemMapper::toEntity)
                        .collect(Collectors.toList()))
                .build();

    }

}
