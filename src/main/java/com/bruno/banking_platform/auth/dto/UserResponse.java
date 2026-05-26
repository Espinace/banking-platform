package com.bruno.banking_platform.auth.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(
    UUID id,
    String name,
    String email
) {
}