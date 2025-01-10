package com.sunday.mecashbank.service;

import com.sunday.mecashbank.DTO.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    List<TransactionResponse> getTransactionHistory(String accountNumber);

}

