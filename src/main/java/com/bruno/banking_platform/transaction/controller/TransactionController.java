package com.bruno.banking_platform.transaction.controller;

import com.bruno.banking_platform.transaction.dto.DepositRequest;
import com.bruno.banking_platform.transaction.dto.TransactionResponse;
import com.bruno.banking_platform.transaction.dto.TransferRequest;
import com.bruno.banking_platform.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse deposit(
            @RequestBody @Valid DepositRequest request
    ) {
        return transactionService.deposit(request);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse transfer(
            @RequestBody @Valid TransferRequest request
    ) {
        return transactionService.transfer(request);
    }
}