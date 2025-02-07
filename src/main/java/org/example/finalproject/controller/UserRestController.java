package org.example.finalproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.finalproject.constants.EndPointPaths;
import org.example.finalproject.controller.docs.UserRestControllerDocumentation;
import org.example.finalproject.dto.JwtTokenResponseDto;
import org.example.finalproject.dto.user.RegisteredResponseDto;
import org.example.finalproject.dto.user.UserLoginDto;
import org.example.finalproject.dto.user.UserRegistrationDto;
import org.example.finalproject.service.JwtService;
import org.example.finalproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndPointPaths.API_USER)
@RequiredArgsConstructor
public class UserRestController implements UserRestControllerDocumentation {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(EndPointPaths.LOGIN)
    public ResponseEntity<JwtTokenResponseDto> login(@Valid @RequestBody UserLoginDto user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtTokenResponseDto(token));
    }

    @PostMapping(EndPointPaths.REGISTER)
    public ResponseEntity<RegisteredResponseDto> register(@Valid @RequestBody UserRegistrationDto user) {
        userService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegisteredResponseDto(user.getUsername(), user.getEmail()));
    }


}
