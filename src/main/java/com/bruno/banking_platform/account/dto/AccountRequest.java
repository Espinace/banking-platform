package com.bruno.banking_platform.account.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AccountRequest(

        @NotNull
        UUID userId

) {
}