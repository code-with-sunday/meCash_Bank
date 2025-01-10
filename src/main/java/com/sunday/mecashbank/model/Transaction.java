package com.sunday.mecashbank.model;

import com.sunday.mecashbank.enums.TRANSACTION_STATUS;
import com.sunday.mecashbank.enums.TRANSACTION_TYPE;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "transactions")
public class Transaction extends AuditBaseEntity{
    @Column(nullable = false, updatable = false, unique = true)
    private UUID transactionId = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TRANSACTION_TYPE transactionType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TRANSACTION_STATUS transactionStatus;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private LocalDateTime transactionDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "target_account_id")
    private Account targetAccount;

    private String note;
}
