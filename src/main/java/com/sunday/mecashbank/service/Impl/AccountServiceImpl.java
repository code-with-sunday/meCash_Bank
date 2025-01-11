package com.sunday.mecashbank.service.Impl;

import com.sunday.mecashbank.DTO.request.AccountRequest;
import com.sunday.mecashbank.DTO.request.DepositRequest;
import com.sunday.mecashbank.DTO.request.TransferRequest;
import com.sunday.mecashbank.DTO.request.WithdrawRequest;
import com.sunday.mecashbank.DTO.response.AccountResponse;
import com.sunday.mecashbank.enums.ACCOUNT_STATUS;
import com.sunday.mecashbank.enums.CURRENCY_TYPE;
import com.sunday.mecashbank.enums.TRANSACTION_STATUS;
import com.sunday.mecashbank.enums.TRANSACTION_TYPE;
import com.sunday.mecashbank.exception.InsufficientFundsException;
import com.sunday.mecashbank.exception.UnAuthorizedException;
import com.sunday.mecashbank.exception.UserNotFoundException;
import com.sunday.mecashbank.model.Account;
import com.sunday.mecashbank.model.Transaction;
import com.sunday.mecashbank.model.User;
import com.sunday.mecashbank.repository.AccountRepository;
import com.sunday.mecashbank.repository.TransactionRepository;
import com.sunday.mecashbank.repository.UserRepository;
import com.sunday.mecashbank.service.AccountService;
import com.sunday.mecashbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnAuthorizedException("Session timed out, please Login first");
        }

        User user2 = userRepository.findById(accountRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + accountRequest.getUserId()));

        Account newAccount = Account.builder()
                .accountNumber(accountRequest.getAccountNumber())
                .currency(CURRENCY_TYPE.valueOf(accountRequest.getCurrency()))
                .accountStatus(ACCOUNT_STATUS.valueOf(accountRequest.getAccountStatus()))
                .balance(accountRequest.getBalance())
                .user(user2)
                .build();

        accountRepository.save(newAccount);

        AccountResponse response = new AccountResponse(newAccount.getAccountNumber(), newAccount.getCurrency(), newAccount.getBalance());

        return response;
    }

    @Override
    public AccountResponse viewAccountBalance(String accountNumber) throws AccountNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnAuthorizedException("Session timed out, please Login first");
        }
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account with number " + accountNumber + " not found."));

        return new AccountResponse(account.getAccountNumber(), account.getCurrency(), account.getBalance());

    }

    @Override
    public AccountResponse deposit(String accountNumber, DepositRequest depositRequest) throws AccountNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnAuthorizedException("Session timed out, please Login first");
        }

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account with number " + accountNumber + " not found."));

        BigDecimal depositAmount = depositRequest.getAmount();
        account.setBalance(account.getBalance().add(depositAmount));

        Transaction depositTransaction = new Transaction();
        depositTransaction.setTransactionType(TRANSACTION_TYPE.DEPOSIT);
        depositTransaction.setAmount(depositAmount);
        depositTransaction.setTransactionStatus(TRANSACTION_STATUS.SUCCESS);
        depositTransaction.setCurrency(String.valueOf(account.getCurrency()));
        depositTransaction.setAccount(account);
        transactionRepository.save(depositTransaction);

        accountRepository.save(account);

        return new AccountResponse(account.getAccountNumber(), account.getCurrency(), account.getBalance());
    }

    @Override
    public AccountResponse withdraw(String accountNumber, WithdrawRequest withdrawRequest) throws AccountNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnAuthorizedException("Session timed out, please Login first");
        }

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account with number " + accountNumber + " not found."));

        BigDecimal withdrawalAmount = withdrawRequest.getAmount();
        if (account.getBalance().compareTo(withdrawalAmount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in the account.");
        }

        account.setBalance(account.getBalance().subtract(withdrawalAmount));

        Transaction withdrawalTransaction = new Transaction();
        withdrawalTransaction.setTransactionType(TRANSACTION_TYPE.WITHDRAWAL);
        withdrawalTransaction.setAmount(withdrawalAmount);
        withdrawalTransaction.setTransactionStatus(TRANSACTION_STATUS.SUCCESS);
        withdrawalTransaction.setCurrency(String.valueOf(account.getCurrency()));
        withdrawalTransaction.setAccount(account);
        transactionRepository.save(withdrawalTransaction);

        accountRepository.save(account);

        return new AccountResponse(account.getAccountNumber(), account.getCurrency(), account.getBalance());
    }

    @Override
    public AccountResponse transfer(String fromAccountNumber, TransferRequest transferRequest) throws AccountNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnAuthorizedException("Session timed out, please Login first");
        }

        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account with number " + fromAccountNumber + " not found."));

        Account toAccount = accountRepository.findByAccountNumber(transferRequest.getToAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account with number " + transferRequest.getToAccountNumber() + " not found."));

        if (fromAccount == null || toAccount == null) {
            throw new AccountNotFoundException("One or both of the accounts were not found.");
        }

        BigDecimal transferAmount = transferRequest.getAmount();
        if (fromAccount.getBalance().compareTo(transferAmount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in the account.");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
        accountRepository.save(fromAccount);

        toAccount.setBalance(toAccount.getBalance().add(transferAmount));
        accountRepository.save(toAccount);

        Transaction transferTransaction = new Transaction();
        transferTransaction.setTransactionType(TRANSACTION_TYPE.TRANSFER);
        transferTransaction.setAmount(transferAmount);
        transferTransaction.setTransactionStatus(TRANSACTION_STATUS.SUCCESS);
        transferTransaction.setCurrency(String.valueOf(fromAccount.getCurrency()));
        transferTransaction.setAccount(fromAccount);
        transferTransaction.setTargetAccount(toAccount);
        transactionRepository.save(transferTransaction);

        return new AccountResponse(fromAccount.getAccountNumber(), fromAccount.getCurrency(), fromAccount.getBalance());
    }

    @Override
    public List<Transaction> getTransactionHistory(String accountNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnAuthorizedException("Session timed out, please Login first");
        }

        return transactionRepository.findByAccount_AccountNumberOrderByTransactionDateDesc(accountNumber);
    }
}
