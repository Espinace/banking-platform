package com.bruno.banking_platform.transaction.repository;

import com.bruno.banking_platform.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository
        extends JpaRepository<Transaction, UUID> {

    List<Transaction> findBySenderAccountIdOrReceiverAccountId(
            UUID senderAccountId,
            UUID receiverAccountId
    );
}