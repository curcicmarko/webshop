package com.example.webshop.controller;

import com.example.webshop.model.dto.CartDto;
import com.example.webshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<List<CartDto>> getCarts() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCarts());
    }

    @GetMapping("/page")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<List<CartDto>> getCartsPage(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartsPage(page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<CartDto> getCart(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCart(id));
    }


    @PutMapping("/update")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR','USER'})")
    public ResponseEntity<CartDto> updateCart(@RequestParam Long productId, @RequestParam int quantity) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.updateCart(productId, quantity));
    }

    @GetMapping("/user-carts")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR','USER'})")
    public ResponseEntity<List<CartDto>> findCartsByUser() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.findCartByUser());
    }

    @DeleteMapping("/{cartId}")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/add-to-cart")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR','USER'})")
    public ResponseEntity<CartDto> addToCart(@RequestParam Long productId, @RequestParam int quantity) {

        return ResponseEntity.status(HttpStatus.OK).body(cartService.addToCart(productId, quantity));

    }

}
