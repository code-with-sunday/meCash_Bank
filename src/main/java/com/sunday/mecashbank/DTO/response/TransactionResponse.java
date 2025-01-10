package com.sunday.mecashbank.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    private UUID transactionId;
    private String transactionType;
    private BigDecimal amount;
    private String transactionStatus;
    private String currency;
    private LocalDateTime transactionDate;
    private String accountNumber;
    private String targetAccountNumber;
    private String note;
}
