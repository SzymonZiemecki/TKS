package com.tks.exception;

public class BusinessLogicException extends IllegalStateException{
    public BusinessLogicException(String message) {
        super(message);
    }
}
