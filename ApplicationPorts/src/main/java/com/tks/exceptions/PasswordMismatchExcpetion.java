package com.tks.exceptions;

import java.util.InputMismatchException;

public class PasswordMismatchExcpetion extends InputMismatchException {
    public PasswordMismatchExcpetion(String message) {
        super(message);
    }
}
