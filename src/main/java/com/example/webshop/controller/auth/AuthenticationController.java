package com.example.webshop.controller.auth;

import com.example.webshop.model.auth.AuthRequest;
import com.example.webshop.model.auth.AuthResponse;
import com.example.webshop.model.auth.TokenRefreshRequest;
import com.example.webshop.model.dto.UserDto;
import com.example.webshop.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @GetMapping("/getUser")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<UserDto> getAuthenticatedUser() {
        return ResponseEntity.status(HttpStatus.OK).body(authService.getAuthenticatedUser());
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Validated AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping(path = "/authenticate/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authService.register(userDto));
    }

    @PostMapping(path = "/authenticate/refreshToken")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody @Validated TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok(authService.refreshToken(tokenRefreshRequest));
    }

    @DeleteMapping(path = "/authenticate/logout")
    public ResponseEntity<Void> logout(){
        authService.singout();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
