package com.example.webshop.model.auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {

    private String jwtToken;

    private String refreshToken;

    public AuthResponse(String jwtToken, String refreshToken) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }

}
