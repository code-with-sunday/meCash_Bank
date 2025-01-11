package com.sunday.mecashbank.controller;

import com.sunday.mecashbank.DTO.request.AccountRequest;
import com.sunday.mecashbank.DTO.request.DepositRequest;
import com.sunday.mecashbank.DTO.request.TransferRequest;
import com.sunday.mecashbank.DTO.request.WithdrawRequest;
import com.sunday.mecashbank.DTO.response.AccountResponse;
import com.sunday.mecashbank.DTO.response.ApiResponse;
import com.sunday.mecashbank.model.Transaction;
import com.sunday.mecashbank.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts/create")
    public ApiResponse<AccountResponse> createAccount(@Valid @RequestBody AccountRequest accountRequest) {
        AccountResponse accountResponse = accountService.createAccount(accountRequest);
        return new ApiResponse<>(true, "201","Account created", accountResponse);
    }

    @GetMapping("/accounts/{accountNumber}/balance")
    public ApiResponse<AccountResponse> viewAccountBalance(@PathVariable String accountNumber) throws AccountNotFoundException {
        AccountResponse response = accountService.viewAccountBalance(accountNumber);
        return new ApiResponse<>(true, "200",
                "Account balance retrieved successfully", response);
    }

    @PostMapping("/accounts/{accountNumber}/deposit")
    public ApiResponse<AccountResponse> deposit(@PathVariable String accountNumber,
                                                @RequestBody DepositRequest depositRequest) throws AccountNotFoundException {
        AccountResponse response = accountService.deposit(accountNumber, depositRequest);
        return new ApiResponse<>(true, "200",
                "Deposit successful", response);
    }

    @PostMapping("/accounts/{accountNumber}/withdraw")
    public ApiResponse<AccountResponse> withdraw(@PathVariable String accountNumber,
                                                 @RequestBody WithdrawRequest withdrawRequest) throws AccountNotFoundException {
        AccountResponse response = accountService.withdraw(accountNumber, withdrawRequest);
        return new ApiResponse<>(true, "200",
                "Withdrawal successful", response);
    }

    @PostMapping("/accounts/{fromAccountNumber}/transfer")
    public ApiResponse<AccountResponse> transfer(@PathVariable String fromAccountNumber,
                                                 @RequestBody TransferRequest transferRequest) throws AccountNotFoundException {
        AccountResponse response = accountService.transfer(fromAccountNumber, transferRequest);
        return new ApiResponse<>(true, "200",
                "Transfer successful", response);
    }

    @GetMapping("/accounts/{accountNumber}/transactions")
    public ApiResponse<List<Transaction>> getTransactionHistory(@PathVariable String accountNumber) {
        List<Transaction> transactions = accountService.getTransactionHistory(accountNumber);
        return new ApiResponse<>(true, "200",
                "Transaction history retrieved successfully", transactions);
    }
}
