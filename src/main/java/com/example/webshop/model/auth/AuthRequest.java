package com.example.webshop.model.auth;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NonNull
    private String email;

    @NonNull
    private String password;


}
