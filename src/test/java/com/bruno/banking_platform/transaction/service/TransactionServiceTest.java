package com.bruno.banking_platform.transaction.service;

import com.bruno.banking_platform.account.domain.Account;
import com.bruno.banking_platform.account.repository.AccountRepository;
import com.bruno.banking_platform.shared.exception.AccountNotFoundException;
import com.bruno.banking_platform.shared.exception.InsufficientBalanceException;
import com.bruno.banking_platform.transaction.domain.Transaction;
import com.bruno.banking_platform.transaction.domain.TransactionType;
import com.bruno.banking_platform.transaction.dto.DepositRequest;
import com.bruno.banking_platform.transaction.dto.TransactionResponse;
import com.bruno.banking_platform.transaction.dto.TransferRequest;
import com.bruno.banking_platform.transaction.dto.WithdrawRequest;
import com.bruno.banking_platform.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void shouldDepositSuccessfully() {

        UUID accountId = UUID.randomUUID();

        Account account = Account.builder()
                .id(accountId)
                .balance(new BigDecimal("500"))
                .build();

        DepositRequest request =
                new DepositRequest(
                        accountId,
                        new BigDecimal("100")
                );

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        TransactionResponse response =
                transactionService.deposit(request);

        assertEquals(
                new BigDecimal("600"),
                account.getBalance()
        );

        assertEquals(
                TransactionType.DEPOSIT,
                response.type()
        );

        verify(accountRepository)
                .save(account);

        verify(transactionRepository)
                .save(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound() {

        UUID accountId = UUID.randomUUID();

        DepositRequest request =
                new DepositRequest(
                        accountId,
                        new BigDecimal("100")
                );

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.deposit(request)
        );
    }

    @Test
    void shouldWithdrawSuccessfully() {

        UUID accountId = UUID.randomUUID();

        Account account = Account.builder()
                .id(accountId)
                .balance(new BigDecimal("500"))
                .build();

        WithdrawRequest request =
                new WithdrawRequest(
                        accountId,
                        new BigDecimal("100")
                );

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        TransactionResponse response =
                transactionService.withdraw(request);

        assertEquals(
                new BigDecimal("400"),
                account.getBalance()
        );

        assertEquals(
                TransactionType.WITHDRAW,
                response.type()
        );

        verify(accountRepository)
                .save(account);

        verify(transactionRepository)
                .save(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {

        UUID accountId = UUID.randomUUID();

        Account account = Account.builder()
                .id(accountId)
                .balance(new BigDecimal("100"))
                .build();

        WithdrawRequest request =
                new WithdrawRequest(
                        accountId,
                        new BigDecimal("200")
                );

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        assertThrows(
                InsufficientBalanceException.class,
                () -> transactionService.withdraw(request)
        );
    }

    @Test
    void shouldTransferSuccessfully() {

        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();

        Account senderAccount = Account.builder()
                .id(senderId)
                .balance(new BigDecimal("1000"))
                .build();

        Account receiverAccount = Account.builder()
                .id(receiverId)
                .balance(new BigDecimal("500"))
                .build();

        TransferRequest request =
                new TransferRequest(
                        senderId,
                        receiverId,
                        new BigDecimal("200")
                );

        when(accountRepository.findById(senderId))
                .thenReturn(Optional.of(senderAccount));

        when(accountRepository.findById(receiverId))
                .thenReturn(Optional.of(receiverAccount));

        TransactionResponse response =
                transactionService.transfer(request);

        assertEquals(
                new BigDecimal("800"),
                senderAccount.getBalance()
        );

        assertEquals(
                new BigDecimal("700"),
                receiverAccount.getBalance()
        );

        assertEquals(
                TransactionType.TRANSFER,
                response.type()
        );

        verify(accountRepository)
                .save(senderAccount);

        verify(accountRepository)
                .save(receiverAccount);

        verify(transactionRepository)
                .save(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenSenderAccountNotFound() {

        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();

        TransferRequest request =
                new TransferRequest(
                        senderId,
                        receiverId,
                        new BigDecimal("100")
                );

        when(accountRepository.findById(senderId))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.transfer(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenReceiverAccountNotFound() {

        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();

        Account senderAccount = Account.builder()
                .id(senderId)
                .balance(new BigDecimal("1000"))
                .build();

        TransferRequest request =
                new TransferRequest(
                        senderId,
                        receiverId,
                        new BigDecimal("100")
                );

        when(accountRepository.findById(senderId))
                .thenReturn(Optional.of(senderAccount));

        when(accountRepository.findById(receiverId))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.transfer(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenTransferToSameAccount() {

        UUID accountId = UUID.randomUUID();

        TransferRequest request =
                new TransferRequest(
                        accountId,
                        accountId,
                        new BigDecimal("100")
                );

        assertThrows(
                RuntimeException.class,
                () -> transactionService.transfer(request)
        );
    }

    @Test
    void shouldReturnStatementSuccessfully() {

        UUID accountId = UUID.randomUUID();

        Account account = Account.builder()
                .id(accountId)
                .balance(new BigDecimal("1000"))
                .build();

        Transaction deposit = Transaction.builder()
                .id(UUID.randomUUID())
                .type(TransactionType.DEPOSIT)
                .amount(new BigDecimal("500"))
                .receiverAccount(account)
                .createdAt(LocalDateTime.now())
                .build();

        Transaction withdraw = Transaction.builder()
                .id(UUID.randomUUID())
                .type(TransactionType.WITHDRAW)
                .amount(new BigDecimal("100"))
                .senderAccount(account)
                .createdAt(LocalDateTime.now())
                .build();

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        when(
                transactionRepository
                        .findBySenderAccountIdOrReceiverAccountId(
                                accountId,
                                accountId
                        )
        ).thenReturn(
                List.of(deposit, withdraw)
        );

        List<TransactionResponse> response =
                transactionService.getStatement(accountId);

        assertEquals(2, response.size());

        assertEquals(
                TransactionType.DEPOSIT,
                response.get(0).type()
        );

        assertEquals(
                TransactionType.WITHDRAW,
                response.get(1).type()
        );

        verify(accountRepository)
                .findById(accountId);

        verify(transactionRepository)
                .findBySenderAccountIdOrReceiverAccountId(
                        accountId,
                        accountId
                );
    }

    @Test
    void shouldThrowExceptionWhenGettingStatementFromNonExistingAccount() {

        UUID accountId = UUID.randomUUID();

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.getStatement(accountId)
        );
    }
}