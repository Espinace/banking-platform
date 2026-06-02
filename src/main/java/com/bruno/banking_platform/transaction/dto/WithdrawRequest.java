package com.bruno.banking_platform.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawRequest(

        @NotNull
        UUID accountId,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal amount
) {
}