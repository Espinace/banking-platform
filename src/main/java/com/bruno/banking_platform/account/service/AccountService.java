package com.bruno.banking_platform.account.service;

import com.bruno.banking_platform.account.domain.Account;
import com.bruno.banking_platform.account.domain.AccountStatus;
import com.bruno.banking_platform.account.dto.AccountResponse;
import com.bruno.banking_platform.account.repository.AccountRepository;
import com.bruno.banking_platform.auth.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponse createAccount() {

        User user = getAuthenticatedUser();

        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .user(user)
                .build();

        accountRepository.save(account);

        return mapToResponse(account);
    }

    public List<AccountResponse> getMyAccounts() {

        User user = getAuthenticatedUser();

        return accountRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private User getAuthenticatedUser() {

        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private String generateAccountNumber() {

        return "ACC-" + System.currentTimeMillis();
    }

    private AccountResponse mapToResponse(Account account) {

        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .status(account.getStatus().name())
                .userId(account.getUser().getId())
                .createdAt(account.getCreatedAt())
                .build();
    }
}