package com.aggregationkeycloak.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        // 1. Публичный доступ (незалогиненные пользователи)
                        .requestMatchers(HttpMethod.GET, "/", "/api/v1/catalogs", "/api/v1/catalogs/**").permitAll()
                        // Остальные запросы требуют аутентификации
                        .anyRequest()
                        .authenticated()
                )
                .anonymous(anonymous -> anonymous
                        // Настройка анонимной сессии
                        .principal("guest")              // Имя анонимного пользователя
                        .authorities("ROLE_guest")       // Роль анонимного пользователя
                        .key("uniqueAnonymousKey")       // Уникальный ключ для анонимного токена
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthConverter)
                        )
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
