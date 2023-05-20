package com.tks.microservices.userservice.repository.exception;

public class LoginAlreadyTakenException extends RepositoryException {
    public LoginAlreadyTakenException(String message) {
        super(message);
    }
}
