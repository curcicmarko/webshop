package com.example.webshop.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "availableQuantity", nullable = false)
    private int availableQuantity;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "pictureUrl", nullable = false)
    private String pictureUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
