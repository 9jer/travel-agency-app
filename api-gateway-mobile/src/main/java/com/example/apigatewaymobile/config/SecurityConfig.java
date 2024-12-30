package com.example.apigatewaymobile.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                        .pathMatchers("/api/v1/auth/**", "/swagger-ui.html",
                                "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**",
                                "/api-docs/**", "/webjars/**","/aggregate/**").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtRequestFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
