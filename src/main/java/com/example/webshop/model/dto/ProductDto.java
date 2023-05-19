package com.example.webshop.model.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {


    private Long id;

    private String name;

    private String description;

    private int availableQuantity;

    private double price;

    private String pictureUrl;

    private String categoryName;

    private Long categoryId;


}
