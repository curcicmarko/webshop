package com.example.webshop.model.dto;

public class OrderItemDto {

    private Long id;
    private int quantity;
    private double price;
    private String productName;
    private Long orderId;

    public OrderItemDto() {
    }

    public OrderItemDto(Long id, int quantity, double price, String productName, Long orderId) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.productName = productName;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
