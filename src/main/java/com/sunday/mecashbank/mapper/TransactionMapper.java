package com.sunday.mecashbank.mapper;

import com.sunday.mecashbank.DTO.request.TransactionRequest;
import com.sunday.mecashbank.DTO.response.TransactionResponse;
import com.sunday.mecashbank.enums.TRANSACTION_STATUS;
import com.sunday.mecashbank.enums.TRANSACTION_TYPE;
import com.sunday.mecashbank.model.Account;
import com.sunday.mecashbank.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionRequest transactionRequest, Account account, Account targetAccount) {
        if (transactionRequest == null || account == null) {
            return null;
        }

        return Transaction.builder()
                .transactionType(TRANSACTION_TYPE.valueOf(transactionRequest.getTransactionType()))
                .amount(transactionRequest.getAmount())
                .transactionStatus(TRANSACTION_STATUS.valueOf(transactionRequest.getTransactionStatus()))
                .currency(transactionRequest.getCurrency())
                .transactionDate(LocalDateTime.now())
                .account(account)
                .targetAccount(targetAccount)
                .note(transactionRequest.getNote())
                .build();
    }

    public TransactionResponse toResponse(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .transactionType(transaction.getTransactionType().name())
                .amount(transaction.getAmount())
                .transactionStatus(transaction.getTransactionStatus().name())
                .currency(transaction.getCurrency())
                .transactionDate(transaction.getTransactionDate())
                .accountNumber(transaction.getAccount().getAccountNumber())
                .targetAccountNumber(transaction.getTargetAccount() != null ? transaction.getTargetAccount().getAccountNumber() : null)
                .note(transaction.getNote())
                .build();
    }
}
