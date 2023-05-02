package com.example.webshop.model.mapper;

import com.example.webshop.model.dto.UserDto;
import com.example.webshop.model.entity.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static User toEntity(UserDto userDto) {
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setAddress(userDto.getAddress());
            user.setCity(userDto.getCity());
            user.setUserRole(userDto.getRole());

//            user.setOrders(userDto.getOrderDtos().stream()
//                    .map(OrderMapper::toEntity)
//                        .collect(Collectors.toList()));
//
//            user.setCarts(userDto.getCartDtos().stream()
//                    .map(CartMapper::toEntity)
//                    .collect(Collectors.toList()));

            return user;
    }

    public static UserDto toDto(User user){

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAddress(user.getAddress());
        userDto.setCity(user.getCity());
        userDto.setRole(user.getUserRole());

//        userDto.setOrderDtos(user.getOrders().stream()
//                .map(OrderMapper::toDto)
//                .collect(Collectors.toList()));
//
//        userDto.setCartDtos(user.getCarts().stream()
//                .map(CartMapper::toDto)
//                .collect(Collectors.toList()));

        return userDto;
    }
}
