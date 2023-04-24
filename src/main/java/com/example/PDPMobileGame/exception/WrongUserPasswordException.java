package com.example.PDPMobileGame.exception;

public class WrongUserPasswordException extends RuntimeException {
    public WrongUserPasswordException(String message) {
        super(message);
    }
}
