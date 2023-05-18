package com.example.webshop.configuration.auth;

import com.example.webshop.exception.NotFoundException;
import com.example.webshop.model.entity.User;
import com.example.webshop.model.mapper.UserMapper;
import com.example.webshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsService {



    private final UserService userService;


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserByEmail(email);
        List<SimpleGrantedAuthority> grantedAuthorities =
                Collections.singletonList(new SimpleGrantedAuthority(user.getUserRole().getName()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), grantedAuthorities);
    }

    public User getUserByEmail(String email) {
        try {
            return userService.findUserByEmail(email);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
