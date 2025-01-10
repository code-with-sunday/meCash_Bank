package com.sunday.mecashbank.controller;

import com.sunday.mecashbank.DTO.response.ApiResponse;
import com.sunday.mecashbank.DTO.response.TransactionResponse;
import com.sunday.mecashbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/transactions/{accountNumber}/history")
    public ApiResponse<List<TransactionResponse>> getTransactionHistory(@PathVariable String accountNumber) {
        List<TransactionResponse> transactions = transactionService.getTransactionHistory(accountNumber);
        return new ApiResponse<>(true, "200",
                "Transaction history retrieved successfully", transactions);
    }
}