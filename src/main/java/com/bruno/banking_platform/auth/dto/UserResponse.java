package com.bruno.banking_platform.auth.dto;

import java.util.UUID;

public record UserResponse(
    UUID id,
    String name,
    String email
) {
}