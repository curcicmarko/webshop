package com.example.webshop.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {

    private Long id;

    private List<OrderItemDto> orderItemDtos;

    private LocalDateTime createdAt;

    private double orderPrice;

}
