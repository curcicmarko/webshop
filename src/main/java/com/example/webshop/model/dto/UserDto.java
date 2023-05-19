package com.example.webshop.model.dto;

import com.example.webshop.model.entity.UserRole;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String address;

    private String city;

    private UserRole role;


}
