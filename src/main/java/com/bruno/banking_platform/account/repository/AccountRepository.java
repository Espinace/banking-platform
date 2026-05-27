package com.bruno.banking_platform.account.repository;

import com.bruno.banking_platform.account.domain.Account;
import com.bruno.banking_platform.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByUser(User user);

    boolean existsByAccountNumber(String accountNumber);
}