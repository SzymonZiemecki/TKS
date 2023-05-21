package com.tks.microservices.orderservice.core.model.exception;

public class BusinessLogicException extends IllegalStateException{
    public BusinessLogicException(String message) {
        super(message);
    }
}
