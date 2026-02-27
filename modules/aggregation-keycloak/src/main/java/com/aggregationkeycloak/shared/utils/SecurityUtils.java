package com.aggregationkeycloak.shared.utils;

import com.aggregationkeycloak.shared.exception.AuthenticationException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityUtils {
    private static final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

    /**
     * Возвращает ID текущего пользователя.
     * Выбрасывает исключение, если пользователь не аутентифицирован (гость или null).
     */
    public static UUID getCurrentUserId() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(auth -> !trustResolver.isAnonymous(auth)) // Блокируем анонимусов
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(principal -> {
                    // 1. Если стандартный JWT
                    if (principal instanceof Jwt jwt) {
                        String subject = jwt.getSubject(); // Claim "sub"
                        if (subject != null) {
                            return UUID.fromString(subject);
                        }
                    }
                    // 2. Если строка (редко, но бывает)
                    else if (principal instanceof String id) {
                        return UUID.fromString(id);
                    }

                    throw new AuthenticationException(
                            "invalid principal type",
                            "principal is not Jwt, got: " +
                                    principal.getClass().getName()
                    );
                })
                .orElseThrow(() -> new AuthenticationException(
                        "user not authenticated",
                        "authentication is null or user is not authenticated"
                ));
    }

    /**
     * Проверяет роль админа только для полноценно аутентифицированных пользователей.
     */
    public static boolean isAdmin() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_admin")))
                .orElse(false);
    }

    /**
     * Проверяет, является ли текущий пользователь полноценно аутентифицированным.
     * Возвращает false для анонимных пользователей и Remember-Me.
     */
    public static boolean isAuthenticated() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
//                .map(Authentication::isAuthenticated) // если расскоментировать блок anonymous в SecurityConfig.securityFilterChain, то всегда возвращает true
                .filter(auth -> !trustResolver.isAnonymous(auth))
                .filter(auth -> !trustResolver.isRememberMe(auth))
                .map(Authentication::isAuthenticated)
                .orElse(false);
    }

    /**
     * Проверяет, была ли аутентификация выполнена через Remember-Me.
     */
    public static boolean isRememberMe() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(trustResolver::isRememberMe)
                .orElse(false);
    }

    /**
     * Проверяет, является ли текущий пользователь анонимным (гостем).
     */
    public static boolean isAnonymous() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(trustResolver::isAnonymous)
                .orElse(true); // Если auth null, считаем анонимным
    }
}