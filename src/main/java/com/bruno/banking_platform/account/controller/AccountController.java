package com.bruno.banking_platform.account.controller;

import com.bruno.banking_platform.account.dto.AccountResponse;
import com.bruno.banking_platform.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountResponse createAccount() {
        return accountService.createAccount();
    }

    @GetMapping("/me")
    public List<AccountResponse> getMyAccounts() {
        return accountService.getMyAccounts();
    }
}