package com.example.webshop.repository;

import com.example.webshop.model.entity.Order;
import com.example.webshop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByUser(User user);

}
