package com.sunday.mecashbank.service;

import com.sunday.mecashbank.DTO.request.DepositRequest;
import com.sunday.mecashbank.DTO.request.TransferRequest;
import com.sunday.mecashbank.DTO.request.WithdrawRequest;
import com.sunday.mecashbank.DTO.response.AccountResponse;
import com.sunday.mecashbank.model.Transaction;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountService {
    AccountResponse viewAccountBalance(String accountNumber) throws AccountNotFoundException;

    AccountResponse deposit(String accountNumber, DepositRequest depositRequest) throws AccountNotFoundException;

    AccountResponse withdraw(String accountNumber, WithdrawRequest withdrawRequest) throws AccountNotFoundException;

    AccountResponse transfer(String fromAccountNumber, TransferRequest transferRequest) throws AccountNotFoundException;

    List<Transaction> getTransactionHistory(String accountNumber);
}
