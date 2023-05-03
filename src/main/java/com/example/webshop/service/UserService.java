package com.example.webshop.service;

import com.example.webshop.model.dto.UserDto;
import com.example.webshop.model.entity.User;
import com.example.webshop.model.mapper.UserMapper;
import com.example.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

        User user = new User();
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

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " does not exist"));

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

    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User with email: " + email + " does not exist");
        }
        return UserMapper.toDto(user);
    }


}
