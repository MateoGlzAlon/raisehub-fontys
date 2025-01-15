package com.fontys.crowdfund.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("EMAIL_IS_ALREADY_REGISTERED");
    }
}
