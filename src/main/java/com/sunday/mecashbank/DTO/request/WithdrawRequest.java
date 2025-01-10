package com.sunday.mecashbank.DTO.request;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WithdrawRequest {

    private BigDecimal amount;

}