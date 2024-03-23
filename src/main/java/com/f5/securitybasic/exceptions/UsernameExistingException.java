package com.f5.securitybasic.exceptions;


public class UsernameExistingException extends RuntimeException {
    public UsernameExistingException(String message) {
        super(message);
    }
}
