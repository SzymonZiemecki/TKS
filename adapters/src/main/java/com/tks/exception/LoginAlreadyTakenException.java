package com.tks.exception;

public class LoginAlreadyTakenException extends RepositoryException {
    public LoginAlreadyTakenException(String message) {
        super(message);
    }
}
