package com.bruno.banking_platform.account.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AccountResponse(

        UUID id,

        String accountNumber,

        BigDecimal balance,

        String status,

        UUID userId,

        LocalDateTime createdAt

) {
}