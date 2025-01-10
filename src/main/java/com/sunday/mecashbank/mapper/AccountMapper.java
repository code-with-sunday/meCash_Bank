package com.sunday.mecashbank.mapper;
import com.sunday.mecashbank.DTO.request.AccountRequest;
import com.sunday.mecashbank.DTO.response.AccountResponse;
import com.sunday.mecashbank.enums.ACCOUNT_STATUS;
import com.sunday.mecashbank.enums.CURRENCY_TYPE;
import com.sunday.mecashbank.model.Account;
import com.sunday.mecashbank.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AccountMapper {

    public Account toEntity(AccountRequest accountRequest, User user) {
        if (accountRequest == null || user == null) {
            return null;
        }

        return Account.builder()
                .accountNumber(accountRequest.getAccountNumber())
                .currency(CURRENCY_TYPE.valueOf(accountRequest.getCurrency()))
                .accountStatus(ACCOUNT_STATUS.valueOf(accountRequest.getAccountStatus()))
                .balance(accountRequest.getBalance())
                .user(user)
                .build();
    }

    public AccountResponse toResponse(Account account) {
        if (account == null) {
            return null;
        }

        return AccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .currency(account.getCurrency().name())
                .balance(account.getBalance())
                .build();
    }
}

