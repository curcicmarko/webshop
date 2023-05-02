package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.OrderItemDto;
import com.example.webshop.model.entity.OrderItem;

public class OrderItemMapper {

    public static OrderItemDto toDto(OrderItem orderItem){

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setProductName(orderItem.getProduct().getName());
        orderItemDto.setOrderId(orderItem.getOrder().getId());

        return orderItemDto;
    }

    public static OrderItem toEntity(OrderItemDto orderItemDto){

        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setQuantity(orderItemDto.getQuantity());

        return orderItem;

    }

}
