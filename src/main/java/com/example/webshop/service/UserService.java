package com.example.webshop.service;

import com.example.webshop.model.dto.UserDto;
import com.example.webshop.model.entity.User;
import com.example.webshop.model.mapper.UserMapper;
import com.example.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getUsersPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> users = userRepository.findAll(pageable);
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsers() {

        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " does not exist"));
        return UserMapper.toDto(user);
    }

    public UserDto createUser(UserDto userDto) {

        System.out.println("Usao u create User");
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAddress(userDto.getAddress());
        user.setCity(userDto.getCity());
        user.setUserRole(userDto.getRole());

        return UserMapper.toDto(userRepository.save(user));


    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with id: %s does not exist", userId)));

        userRepository.delete(user);

    }

    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " does not exist"));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAddress(userDto.getAddress());
        user.setCity(userDto.getCity());
        user.setUserRole(userDto.getRole());
        userRepository.save(user);

        return UserMapper.toDto(user);

    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
           throw new IllegalArgumentException("User with email: " + email + " does not exist");
        }
        return user;
    }

    public boolean userWithEmailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }



}
