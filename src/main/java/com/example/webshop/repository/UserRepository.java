package com.example.webshop.repository;

import com.example.webshop.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {

     User findByEmail(String email);
     Page<User> findAll(Pageable pageable);

}
