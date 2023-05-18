package com.example.webshop.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    private Long id;

    private List<OrderItemDto> orderItemDtos;

    private LocalDateTime createdAt;

    private double orderPrice;

    public OrderDto() {
    }

    public OrderDto(Long id, List<OrderItemDto> orderItemDtos, LocalDateTime createdAt, double orderPrice) {
        this.id = id;

        this.orderItemDtos = orderItemDtos;
        this.createdAt = createdAt;
        this.orderPrice = orderPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<OrderItemDto> getOrderItemDtos() {
        return orderItemDtos;
    }

    public void setOrderItemDtos(List<OrderItemDto> orderItemDtos) {
        this.orderItemDtos = orderItemDtos;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }
}
