package com.bruno.banking_platform.transaction.dto;

import com.bruno.banking_platform.transaction.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(

        UUID id,

        TransactionType type,

        BigDecimal amount,

        UUID senderAccountId,

        UUID receiverAccountId,

        LocalDateTime createdAt

) {
}