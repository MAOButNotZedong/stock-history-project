package org.example.finalproject.security;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.config.CustomAuthenticationEntryPoint;
import org.example.finalproject.constants.EndPointPaths;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@TestConfiguration
@Profile("component-test")
public class SecurityConfigForTests {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private static final String[] PERMIT_ALL_PATHS = {
            EndPointPaths.API_USER_LOGIN,
            EndPointPaths.API_USER_REGISTER,
            EndPointPaths.SWAGGER_UI,
            EndPointPaths.API_DOCS
    };

    @Bean
    @Primary
    public SecurityFilterChain securityFilterChainForTests(HttpSecurity http) throws Exception {
        http.securityMatcher("/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(PERMIT_ALL_PATHS).permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(exceptionHandler ->
                        exceptionHandler.authenticationEntryPoint(customAuthenticationEntryPoint))
                .httpBasic(Customizer.withDefaults());
        return http.build();

    }

}
