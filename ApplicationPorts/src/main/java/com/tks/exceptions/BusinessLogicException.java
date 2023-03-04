package com.tks.exceptions;

public class BusinessLogicException extends IllegalStateException{
    public BusinessLogicException(String message) {
        super(message);
    }
}
