package com.example.webshop.repository;

import com.example.webshop.model.entity.Cart;
import com.example.webshop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    public List<Cart> findByUser(User user);
}
