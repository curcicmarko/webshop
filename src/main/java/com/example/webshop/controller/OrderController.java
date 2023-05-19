package com.example.webshop.controller;

import com.example.webshop.model.dto.OrderDto;
import com.example.webshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrders());
    }

    @GetMapping("/page")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<List<OrderDto>> getOrders(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersPage(page, size));
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(orderId));
    }

    @GetMapping("/user-orders")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR','USER'})")
    public ResponseEntity<List<OrderDto>> findOrdersByUser() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findOrdersByUser());
    }

    @GetMapping("/user-orders/page")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR','USER'})")
    public ResponseEntity<List<OrderDto>> findOrdersByUser(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.status(HttpStatus.OK).body(orderService.findOrdersByUserPage(page, size));
    }

    @PostMapping("/create")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR','USER'})")
    public ResponseEntity<OrderDto> createOrder() {

        return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder());
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<Void> deleteCart(@PathVariable Long orderId) {

        orderService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
