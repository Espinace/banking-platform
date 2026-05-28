package com.bruno.banking_platform.transaction.service;

import com.bruno.banking_platform.account.domain.Account;
import com.bruno.banking_platform.account.repository.AccountRepository;
import com.bruno.banking_platform.transaction.domain.Transaction;
import com.bruno.banking_platform.transaction.domain.TransactionType;
import com.bruno.banking_platform.transaction.dto.DepositRequest;
import com.bruno.banking_platform.transaction.dto.TransactionResponse;
import com.bruno.banking_platform.transaction.dto.TransferRequest;
import com.bruno.banking_platform.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public TransactionResponse deposit(DepositRequest request) {

        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        account.setBalance(
                account.getBalance().add(request.amount())
        );

        Transaction transaction = Transaction.builder()
                .amount(request.amount())
                .type(TransactionType.DEPOSIT)
                .receiverAccount(account)
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        accountRepository.save(account);

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

    @Transactional
    public TransactionResponse transfer(TransferRequest request) {

        if (request.senderAccountId().equals(request.receiverAccountId())) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        Account senderAccount = accountRepository
                .findById(request.senderAccountId())
                .orElseThrow(() ->
                        new RuntimeException("Sender account not found"));

        Account receiverAccount = accountRepository
                .findById(request.receiverAccountId())
                .orElseThrow(() ->
                        new RuntimeException("Receiver account not found"));

        if (senderAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new RuntimeException("Insufficient balance");
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
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

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