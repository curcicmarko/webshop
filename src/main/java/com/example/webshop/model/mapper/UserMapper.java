package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.UserDto;
import com.example.webshop.model.entity.User;

public class UserMapper {

    public static User toEntity(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .address(userDto.getAddress())
                .city(userDto.getCity())
                .userRole(userDto.getRole())
                .build();
    }


    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .address(user.getAddress())
                .city(user.getCity())
                .role(user.getUserRole())
                .build();
    }
}
