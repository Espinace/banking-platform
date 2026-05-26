package com.bruno.banking_platform.auth.service;

import com.bruno.banking_platform.auth.domain.User;
import com.bruno.banking_platform.auth.dto.LoginRequest;
import com.bruno.banking_platform.auth.dto.LoginResponse;
import com.bruno.banking_platform.auth.dto.RegisterRequest;
import com.bruno.banking_platform.auth.dto.UserResponse;
import com.bruno.banking_platform.auth.exception.EmailAlreadyExistsException;
import com.bruno.banking_platform.auth.exception.InvalidCredentialsException;
import com.bruno.banking_platform.auth.repository.UserRepository;
import com.bruno.banking_platform.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthService {
    private static final Logger log =
            LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest request) {

        userRepository.findByEmail(request.email())
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException();
                });

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        log.info("Creating user with email {}", request.email());

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new InvalidCredentialsException("User not found"));

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(token);
    }
}