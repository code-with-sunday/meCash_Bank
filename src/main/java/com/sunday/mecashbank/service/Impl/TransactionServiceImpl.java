package com.sunday.mecashbank.service.Impl;

import com.sunday.mecashbank.DTO.response.TransactionResponse;
import com.sunday.mecashbank.exception.UnAuthorizedException;
import com.sunday.mecashbank.model.Transaction;
import com.sunday.mecashbank.repository.TransactionRepository;
import com.sunday.mecashbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<TransactionResponse> getTransactionHistory(String accountNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnAuthorizedException("Session timed out, please Login first");
        }
        List<Transaction> transactions = transactionRepository.findByAccount_AccountNumberOrderByTransactionDateDesc(accountNumber);

        return transactions.stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getTransactionId(),
                        transaction.getTransactionType().name(),
                        transaction.getAmount(),
                        transaction.getTransactionStatus().name(),
                        transaction.getCurrency(),
                        transaction.getTransactionDate(),
                        transaction.getAccount().getAccountNumber(),
                        transaction.getTargetAccount() != null ? transaction.getTargetAccount().getAccountNumber() : null, // Get target account number if available
                        transaction.getNote()
                ))
                .collect(Collectors.toList());
    }
}