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

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
