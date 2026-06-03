package com.bruno.banking_platform.auth.service;
import com.bruno.banking_platform.account.repository.AccountRepository;
import com.bruno.banking_platform.auth.domain.User;
import com.bruno.banking_platform.auth.dto.LoginRequest;
import com.bruno.banking_platform.auth.dto.LoginResponse;
import com.bruno.banking_platform.auth.dto.RegisterRequest;
import com.bruno.banking_platform.auth.dto.UserResponse;
import com.bruno.banking_platform.auth.exception.EmailAlreadyExistsException;
import com.bruno.banking_platform.auth.exception.InvalidCredentialsException;
import com.bruno.banking_platform.auth.repository.UserRepository;
import com.bruno.banking_platform.security.JwtService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterSuccessfully() {

        RegisterRequest request =
                new RegisterRequest(
                        "Bruno",
                        "bruno@email.com",
                        "123456"
                );

        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .email(request.email())
                .password("encoded-password")
                .build();

        when(userRepository.findByEmail(request.email()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(request.password()))
                .thenReturn("encoded-password");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        UserResponse response =
                authService.register(request);

        assertNotNull(response);

        assertEquals(
                request.email(),
                response.email()
        );

        verify(userRepository)
                .save(any(User.class));

        verify(accountRepository)
                .save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        RegisterRequest request =
                new RegisterRequest(
                        "Bruno",
                        "bruno@email.com",
                        "123456"
                );

        User existingUser = User.builder()
                .email(request.email())
                .build();

        when(userRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(existingUser));

        assertThrows(
                EmailAlreadyExistsException.class,
                () -> authService.register(request)
        );
    }

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request =
                new LoginRequest(
                        "bruno@email.com",
                        "123456"
                );

        User user = User.builder()
                .email(request.email())
                .password("encoded-password")
                .build();

        when(userRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )).thenReturn(true);

        when(jwtService.generateToken(user))
                .thenReturn("fake-jwt");

        LoginResponse response =
                authService.login(request);

        assertEquals(
                "fake-jwt",
                response.token()
        );
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        LoginRequest request =
                new LoginRequest(
                        "bruno@email.com",
                        "123456"
                );

        when(userRepository.findByEmail(request.email()))
                .thenReturn(Optional.empty());

        assertThrows(
                InvalidCredentialsException.class,
                () -> authService.login(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {

        LoginRequest request =
                new LoginRequest(
                        "bruno@email.com",
                        "123456"
                );

        User user = User.builder()
                .email(request.email())
                .password("encoded-password")
                .build();

        when(userRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )).thenReturn(false);

        assertThrows(
                InvalidCredentialsException.class,
                () -> authService.login(request)
        );
    }


}