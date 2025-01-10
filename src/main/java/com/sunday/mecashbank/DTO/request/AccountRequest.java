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
public class AccountRequest {

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Currency type is required")
    private String currency;

    @NotNull(message = "Account status is required")
    private String accountStatus;

    @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be zero or positive")
    private BigDecimal balance;

    @NotNull(message = "User ID is required")
    private Long userId;
}
