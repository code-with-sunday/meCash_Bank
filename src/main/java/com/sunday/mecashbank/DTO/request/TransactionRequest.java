package com.sunday.mecashbank.DTO.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {

    @NotNull(message = "Transaction type is required")
    private String transactionType;

    @DecimalMin(value = "0.01", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Transaction status is required")
    private String transactionStatus;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotNull(message = "Account ID is required")
    private Long accountId;

    private Long targetAccountId;

    private String note;
}
