package com.sunday.mecashbank.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountNumberGenerator {

    public String generateAccountNumber() {
        Random random = new Random();
        long accountNumber = 1000000000L + (long) (random.nextDouble() * 8999999999L);
        return String.valueOf(accountNumber);
    }
}