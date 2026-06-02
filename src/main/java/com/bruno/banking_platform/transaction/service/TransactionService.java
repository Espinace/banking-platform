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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public TransactionResponse deposit(DepositRequest request) {

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found"));

        account.setBalance(
                account.getBalance().add(request.amount())
        );

        Transaction transaction = Transaction.builder()
                .amount(request.amount())
                .type(TransactionType.DEPOSIT)
                .receiverAccount(account)
                .description("Deposit to account " + account.getId())
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        accountRepository.save(account);

        return mapToResponse(transaction);
    }

    @Transactional
    public TransactionResponse transfer(TransferRequest request) {

        if (request.senderAccountId().equals(request.receiverAccountId())) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        Account senderAccount = accountRepository
                .findById(request.senderAccountId())
                .orElseThrow(() ->
                        new AccountNotFoundException("Sender account not found"));

        Account receiverAccount = accountRepository
                .findById(request.receiverAccountId())
                .orElseThrow(() ->
                        new AccountNotFoundException("Receiver account not found"));

        if (senderAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        senderAccount.setBalance(
                senderAccount.getBalance().subtract(request.amount())
        );

        receiverAccount.setBalance(
                receiverAccount.getBalance().add(request.amount())
        );

        Transaction transaction = Transaction.builder()
                .amount(request.amount())
                .type(TransactionType.TRANSFER)
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .description("Transfer from account " + senderAccount.getId() + " to account " + receiverAccount.getId())
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        return mapToResponse(transaction);
    }

    @Transactional
    public TransactionResponse withdraw(WithdrawRequest request) {

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found"));

        if (account.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setBalance(
                account.getBalance().subtract(request.amount())
        );

        Transaction transaction = Transaction.builder()
                .amount(request.amount())
                .type(TransactionType.WITHDRAW)
                .description("Withdraw from account " + account.getId())
                .senderAccount(account)
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        accountRepository.save(account);

        return mapToResponse(transaction);
    }

    public List<TransactionResponse> getStatement(UUID accountId) {

        accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found"));

        return transactionRepository
                .findBySenderAccountIdOrReceiverAccountId(
                        accountId,
                        accountId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TransactionResponse mapToResponse(Transaction transaction) {

        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getSenderAccount() != null
                        ? transaction.getSenderAccount().getId()
                        : null,
                transaction.getReceiverAccount() != null
                        ? transaction.getReceiverAccount().getId()
                        : null,
                transaction.getCreatedAt()
        );
    }
}