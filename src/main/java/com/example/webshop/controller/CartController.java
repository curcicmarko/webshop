package com.example.webshop.controller;

import com.example.webshop.model.dto.CartDto;
import com.example.webshop.service.CartService;
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
    public ResponseEntity<List<CartDto>> getCarts() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCarts());
    }

    @GetMapping("/page")
    public ResponseEntity<List<CartDto>> getCartsPage(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartsPage(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCart(id));
    }

    @PostMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartDto> addToCart(@PathVariable Long cartId, @PathVariable Long productId,
                                             @RequestParam int quantity, @RequestParam Long userId) {

        return ResponseEntity.status(HttpStatus.OK).body(cartService.addToCart(cartId, productId, quantity, userId));

    }

    @PutMapping("/{cartId}/update")
    public ResponseEntity<CartDto> updateCart(@PathVariable Long cartId,
                                              @RequestParam Long productId, @RequestParam int quantity) {

        return ResponseEntity.status(HttpStatus.OK).body(cartService.updateCart(cartId, productId, quantity));

    }

    @GetMapping("/user-carts/{userId}")
    public ResponseEntity<List<CartDto>> findCartsByUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.findCartByUser(userId));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
