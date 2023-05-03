package com.example.webshop.controller;

import com.example.webshop.model.dto.OrderDto;
import com.example.webshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(orderId));
    }

    @GetMapping("/user-orders/{userId}")
    public ResponseEntity<List<OrderDto>> findOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findOrdersByUser(userId));
    }

    @PostMapping("/create/{cartId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long cartId) {

        return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder(cartId));

    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
