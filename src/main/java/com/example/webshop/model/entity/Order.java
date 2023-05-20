package com.example.webshop.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "[order]")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Column
    private LocalDateTime createdAt;

    @Column(name = "orderPrice")
    private double orderPrice;

    public Order() {
        this.orderItems = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

}
