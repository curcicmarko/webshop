package com.example.webshop.service.auth;

import com.example.webshop.configuration.auth.JwtProvider;
import com.example.webshop.exception.BadRequestException;
import com.example.webshop.exception.NotFoundException;
import com.example.webshop.exception.UnauthorizedException;
import com.example.webshop.model.auth.AuthRequest;
import com.example.webshop.model.auth.AuthResponse;
import com.example.webshop.model.auth.AuthToken;
import com.example.webshop.model.auth.TokenRefreshRequest;
import com.example.webshop.model.dto.UserDto;
import com.example.webshop.model.entity.User;
import com.example.webshop.model.mapper.UserMapper;
import com.example.webshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final AuthTokenService authTokenService;


    public UserDto getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return UserMapper.toDto(userService.findUserByEmail(email));
    }

    public User getAuthenticatedUserModel() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userService.findUserByEmail(email);
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        if (authRequest.getEmail().isBlank() || authRequest.getPassword().isBlank()) {
            throw new BadRequestException("Invalid credentials");
        }
        User user = findUserByEmail(authRequest.getEmail());

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }
        if (authTokenService.userHasAccessToken(user)) {
            return generateAuthTokenFromExistingToken(user);
        } else {
            return generateNewAuthToken(user);
        }
    }

    private AuthResponse generateNewAuthToken(User user) {
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmail(), user.getUserRole().getName());
        String accessToken = jwtProvider.generateToken(user.getEmail(), user.getUserRole().getName());
        saveAuthToken(user, accessToken, refreshToken);
        return AuthResponse.builder().jwtToken(accessToken).refreshToken(refreshToken).build();
    }

    private AuthResponse generateAuthTokenFromExistingToken(User user) {
        AuthToken authToken = authTokenService.getAuthTokenByUser(user);
        if (!jwtProvider.isTokenExpired(authToken.getAccessToken())) {
            return AuthResponse.builder()
                    .jwtToken(authToken.getAccessToken()).refreshToken(authToken.getRefreshToken()).build();
        }
        if (!jwtProvider.isTokenExpired(authToken.getRefreshToken())) {
            String newAccessToken = generateNewAccessToken(user);
            return AuthResponse.builder()
                    .jwtToken(newAccessToken).refreshToken(authToken.getRefreshToken()).build();
        }
        String newAccessToken = generateNewAccessToken(user);
        String newRefreshToken = generateNewRefreshToken(user);

        return AuthResponse.builder().jwtToken(newAccessToken).refreshToken(newRefreshToken).build();
    }

    private String generateNewAccessToken(User user) {
        AuthToken authToken = authTokenService.getAuthTokenByUser(user);
        String accessToken = jwtProvider.generateToken(user.getEmail(), user.getUserRole().getName());
        authToken.setAccessToken(accessToken);
        authTokenService.save(authToken);

        return accessToken;
    }

    private String generateNewRefreshToken(User user) {
        AuthToken authToken = authTokenService.getAuthTokenByUser(user);
        String newRefreshToken = jwtProvider.generateRefreshToken(user.getEmail(), user.getUserRole().getName());
        authToken.setRefreshToken(newRefreshToken);
        authTokenService.save(authToken);

        return newRefreshToken;
    }

    public void saveAuthToken(User user, String accessToken, String refreshToken) {
        if (user.getEmail().isBlank() || user.getPassword().isBlank()) {
            throw new BadRequestException("Invalid credentials");
        }

        AuthToken authToken = new AuthToken();
        authToken.setUser(user);
        authToken.setAccessToken(accessToken);
        authToken.setRefreshToken(refreshToken);
        authTokenService.save(authToken);

    }

    private User findUserByEmail(String email) {
        try {
            return userService.findUserByEmail(email);
        } catch (NotFoundException e) {
            throw new BadRequestException("Invalid credentials");
        }
    }

    public boolean hasAccess(Set<String> allowedRoles) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findUserByEmail(email);
        return allowedRoles.contains(user.getUserRole().getName());
    }

    public UserDto register(UserDto userDto) {
        if (userWithEmailExists(userDto.getEmail())) {
            throw new BadRequestException(String.format("User with email %s already exists", userDto.getEmail()));
        }
        User user = UserMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        return userService.createUser(UserMapper.toDto(user));
    }

    public boolean userWithEmailExists(String email) {
        return userService.userWithEmailExists(email);
    }

    public void singout() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findUserByEmail(email);
        AuthToken authToken = authTokenService.getAuthTokenByUser(user);
        authTokenService.delete(authToken);
    }

    public AuthResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        if (tokenRefreshRequest.getRefreshToken().isBlank()) {
            throw new BadRequestException("Invalid credentials");
        }
        String token = tokenRefreshRequest.getRefreshToken();
        AuthToken authToken = authTokenService.getAuthTokenByRefreshToken(token);
        if (authTokenService.refreshTokenExpired(authToken)) {
            throw new UnauthorizedException("Not authorized");
        }
        User user = authToken.getUser();
        String jwtToken = jwtProvider.generateToken(user.getEmail(), user.getUserRole().getName());
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmail(), user.getUserRole().getName());
        authToken.setAccessToken(jwtToken);
        authToken.setRefreshToken(refreshToken);
        authTokenService.save(authToken);
        return AuthResponse.builder().jwtToken(jwtToken).refreshToken(refreshToken).build();
    }


}
