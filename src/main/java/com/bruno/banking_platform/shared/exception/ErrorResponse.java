package com.bruno.banking_platform.shared.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        LocalDateTime timestamp,
        Integer status,
        String message
) {
}