package com.example.webshop.configuration.auth;

import com.example.webshop.exception.UnauthorizedException;
import com.example.webshop.model.auth.AuthToken;
import com.example.webshop.model.entity.User;
import com.example.webshop.service.auth.AuthTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {


    private final JwtProvider jwtProvider;

    private final UserDetailsService userDetailsService;

    private final AuthTokenService authTokenService;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (!isNull(token) && !authenticated(token)) {

            throw new UnauthorizedException("Not authorized");
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    private boolean authenticated(String token) {
        try {
            String email = jwtProvider.getEmailFromToken(token);
            User user = userDetailsService.getUserByEmail(email);
            AuthToken authToken = authTokenService.getAuthTokenByUser(user);
            if (!authToken.getAccessToken().equals(jwtProvider.removeBearerFromToken(token))) {
                return false;
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    email, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (Exception e) {
            // exception not relevant since unauthorized will be returned
            return false;
        }
    }
}
