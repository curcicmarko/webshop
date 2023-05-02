package com.example.webshop.model.dto;

public class CartItemDto {

    private Long id;

   // private ProductDto productDto;

   // private CartDto cartDto;

    private String productName;
    private int quantity;
    private double price;

    public CartItemDto() {
    }

    public CartItemDto(Long id, Long productId, String productName, int quantity, double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.productName = productName;
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

//    public ProductDto getProductDto() {
//        return productDto;
//    }
//
//    public void setProductDto(ProductDto productDto) {
//        this.productDto = productDto;
//    }
//
//    public CartDto getCartDto() {
//        return cartDto;
//    }
//
//    public void setCartDto(CartDto cartDto) {
//        this.cartDto = cartDto;
//    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
