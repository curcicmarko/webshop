package com.example.webshop.controller;

import com.example.webshop.model.dto.CartDto;
import com.example.webshop.model.entity.Cart;
import com.example.webshop.service.CartService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartDto>> getCarts(){
        return  ResponseEntity.status(HttpStatus.OK).body(cartService.getCarts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long id){
        return  ResponseEntity.status(HttpStatus.OK).body(cartService.getCart(id));
    }

    @PostMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartDto> addToCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity, @RequestParam Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.addToCart(cartId,productId,quantity,userId));

    }
}
