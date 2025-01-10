package com.sunday.mecashbank.model;

import com.sunday.mecashbank.enums.ACCOUNT_STATUS;
import com.sunday.mecashbank.enums.CURRENCY_TYPE;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "accounts")
public class Account extends AuditBaseEntity{
    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CURRENCY_TYPE currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ACCOUNT_STATUS accountStatus;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();
}
