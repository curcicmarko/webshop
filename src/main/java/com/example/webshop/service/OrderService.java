package com.example.webshop.service;

import com.example.webshop.exception.NotFoundException;
import com.example.webshop.model.dto.OrderDto;
import com.example.webshop.model.entity.*;
import com.example.webshop.model.mapper.OrderMapper;
import com.example.webshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
                .orElseThrow(() -> new NotFoundException(String.format("Order with id: %s  does not exists", orderId)));

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

    public OrderDto createOrder() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User loggedUser = userRepository.findByEmail(email);
        Cart cart = loggedUser.getCarts().get(0);


        Order order = createNewOrder(cart, loggedUser);
        saveOrder(order);

        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = createOrderItem(cartItem, order);
            saveOrderItem(orderItem);

            updateProductQuantity(cartItem.getProduct(), orderItem.getQuantity());

            order.getOrderItems().add(orderItem);
        }

        cartRepository.delete(cart);

        return OrderMapper.toDto(order);
    }

    private Order createNewOrder(Cart cart, User loggedUser) {
        Order order = new Order();
        order.setOrderPrice(cart.getTotalPrice());
        order.setUser(loggedUser);

        LocalDateTime localDateTime = order.getCreatedAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);
        LocalDateTime newLocalDateTime = LocalDateTime.parse(formattedDateTime, formatter);
        order.setCreatedAt(newLocalDateTime);

        return order;
    }

    private void saveOrder(Order order) {
        orderRepository.save(order);
    }

    private OrderItem createOrderItem(CartItem cartItem, Order order) {
        return OrderItem.builder()
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .product(cartItem.getProduct())
                .order(order)
                .build();
    }


    private void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    private void updateProductQuantity(Product product, int quantity) {
        product.setAvailableQuantity(product.getAvailableQuantity() - quantity);
        productRepository.save(product);
    }


    public List<OrderDto> findOrdersByUser() {

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User loggedUser = userRepository.findByEmail(email);

        return orderRepository.findByUser(loggedUser).stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> findOrdersByUserPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User loggedUser = userRepository.findByEmail(email);

        Page<Order> orders = orderRepository.findByUser(loggedUser, pageable);

        return orders.stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());


    }

    public void deleteOrder(Long orderId) {
        orderRepository.delete(orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(String.format("Order with id: %s  does not exists", orderId))));
    }

}
