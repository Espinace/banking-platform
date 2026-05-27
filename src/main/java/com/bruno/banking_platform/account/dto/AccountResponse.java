package com.bruno.banking_platform.account.dto;

import com.bruno.banking_platform.account.domain.AccountStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AccountResponse(

        UUID id,
        String accountNumber,
        BigDecimal balance,
        AccountStatus status,
        LocalDateTime createdAt

) {
}