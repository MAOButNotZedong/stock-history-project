package org.example.finalproject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.finalproject.config.JwtFilter;
import org.example.finalproject.service.JwtService;
import org.example.finalproject.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Configuration
@Profile("component-test")
@Primary
public class JwtFilterForTests extends JwtFilter {

    public JwtFilterForTests(JwtService jwtService, UserService userService, HandlerExceptionResolver handlerExceptionResolver) {
        super(jwtService, userService, handlerExceptionResolver);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        super.doFilterInternal(request, response, filterChain);
    }
}
