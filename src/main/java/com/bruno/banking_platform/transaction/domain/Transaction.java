package com.bruno.banking_platform.transaction.domain;

import com.bruno.banking_platform.account.domain.Account;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}