package com.example.webshop.repository;

import com.example.webshop.model.entity.Order;
import com.example.webshop.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    Page<Order> findAll(Pageable pageable);

    Page<Order> findByUser(User user, Pageable pageable);
}
