package com.bruno.banking_platform.account.service;

import com.bruno.banking_platform.account.domain.Account;
import com.bruno.banking_platform.account.domain.AccountStatus;
import com.bruno.banking_platform.account.dto.AccountResponse;
import com.bruno.banking_platform.account.repository.AccountRepository;
import com.bruno.banking_platform.auth.domain.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    private User mockAuthenticatedUser() {

        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Bruno")
                .email("bruno@email.com")
                .build();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        return user;
    }

    @Test
    void shouldCreateAccountSuccessfully() {

        User user = mockAuthenticatedUser();

        AccountResponse response =
                accountService.createAccount();

        assertNotNull(response);

        assertEquals(
                user.getId(),
                response.userId()
        );

        assertEquals(
                BigDecimal.ZERO,
                response.balance()
        );

        verify(accountRepository)
                .save(any(Account.class));
    }

    @Test
    void shouldReturnUserAccounts() {

        User user = mockAuthenticatedUser();

        Account account1 = Account.builder()
                .id(UUID.randomUUID())
                .accountNumber("ACC-1")
                .balance(BigDecimal.TEN)
                .user(user)
                .status(AccountStatus.ACTIVE)
                .build();

        Account account2 = Account.builder()
                .id(UUID.randomUUID())
                .accountNumber("ACC-2")
                .balance(BigDecimal.ONE)
                .user(user)
                .status(AccountStatus.ACTIVE)
                .build();

        when(accountRepository.findByUser(user))
                .thenReturn(List.of(account1, account2));

        List<AccountResponse> response =
                accountService.getMyAccounts();

        assertEquals(2, response.size());

        verify(accountRepository)
                .findByUser(user);
    }


}