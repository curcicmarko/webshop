package com.example.webshop.service;

import com.example.webshop.model.dto.CartDto;
import com.example.webshop.model.dto.OrderDto;
import com.example.webshop.model.entity.*;
import com.example.webshop.model.mapper.CartMapper;
import com.example.webshop.model.mapper.OrderMapper;
import com.example.webshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with Id: " + orderId + " does not exist"));

        return OrderMapper.toDto(order);

    }

    public List<OrderDto> getOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderDto createOrder(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart with Id: " + cartId + " does not exist"));

        Order order = new Order();
        order.setOrderPrice(cart.getTotalPrice());
        order.setUser(cart.getUser());

        LocalDateTime localDateTime = order.getCreatedAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);
        LocalDateTime newLocalDateTime = LocalDateTime.parse(formattedDateTime, formatter);
        order.setCreatedAt(newLocalDateTime);

        orderRepository.save(order);

        List<CartItem> cartItems = cart.getCartItems();

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);

            orderItemRepository.save(orderItem);
            order.getOrderItems().add(orderItem);

            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product with Id: " + cartItem.getProduct().getId() + " does not exist"));
            product.setAvailableQuantity(product.getAvailableQuantity() - orderItem.getQuantity());
            productRepository.save(product);

        }
        orderRepository.save(order);

        return OrderMapper.toDto(order);
    }


    public List<OrderDto> findOrdersByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with Id: " + userId + " does not exist"));

        return orderRepository.findByUser(user).stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> findOrdersByUserPage(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with Id: " + userId + " does not exist"));

        Page<Order> orders = orderRepository.findByUser(user, pageable);

        return orders.stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());


    }

    public void deleteOrder(Long orderId) {
        orderRepository.delete(orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with Id: " + orderRepository.findById(orderId) + " does not exist")));
    }

}
