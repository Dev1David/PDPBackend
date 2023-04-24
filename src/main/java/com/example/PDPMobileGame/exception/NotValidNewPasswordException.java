package com.example.PDPMobileGame.exception;

public class NotValidNewPasswordException extends RuntimeException {
    public NotValidNewPasswordException (String message) {
        super (message);
    }
}
