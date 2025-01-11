package com.sunday.mecashbank.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String msg){
        super(msg);
    }

}
