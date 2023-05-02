package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.OrderDto;
import com.example.webshop.model.entity.Order;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setOrderPrice(order.getOrderPrice());
        orderDto.setCreatedAt(order.getCreatedAt());
        //orderDto.setUserDto(UserMapper.toDto(order.getUser()));
        orderDto.setOrderItemDtos(order.getOrderItems().stream()
                .map(OrderItemMapper::toDto)
                .collect(Collectors.toList()));

        return orderDto;
    }

    public static Order toEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderPrice(orderDto.getOrderPrice());
        order.setCreatedAt(orderDto.getCreatedAt());
       // order.setUser(UserMapper.toEntity(orderDto.getUserDto()));

        order.setOrderItems(orderDto.getOrderItemDtos().stream()
                .map(OrderItemMapper::toEntity)
                .collect(Collectors.toList()));

        return order;
    }
}
