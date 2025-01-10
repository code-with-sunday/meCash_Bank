package com.sunday.mecashbank.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String msg){
        super(msg);
    }

}
