package com.bruno.banking_platform.auth.service;

import com.bruno.banking_platform.auth.domain.User;
import com.bruno.banking_platform.auth.dto.UserResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final UserService userService =
            new UserService();

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldReturnCurrentUser() {

        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Bruno")
                .email("bruno@email.com")
                .build();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        UserResponse response =
                userService.getCurrentUser();

        assertEquals(
                user.getEmail(),
                response.email()
        );

        assertEquals(
                user.getName(),
                response.name()
        );
    }
}