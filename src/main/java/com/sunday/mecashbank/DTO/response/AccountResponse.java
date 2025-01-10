package com.sunday.mecashbank.DTO.response;

import com.sunday.mecashbank.enums.CURRENCY_TYPE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {

    private String accountNumber;
    private CURRENCY_TYPE currency;
    private BigDecimal balance;
}