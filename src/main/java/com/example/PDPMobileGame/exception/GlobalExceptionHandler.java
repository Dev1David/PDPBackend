package com.example.PDPMobileGame.exception;

import com.example.PDPMobileGame.error_response.UserErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> somethingWentWrongHandler(Exception e) {
        UserErrorResponse errorResponse = new UserErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        UserErrorResponse errorResponse = new UserErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughFundsException.class)
    public ResponseEntity<UserErrorResponse> handleNotEnoughFundsException(NotEnoughFundsException ex) {
        UserErrorResponse errorResponse = new UserErrorResponse(HttpStatus.PAYMENT_REQUIRED.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(WrongUserPasswordException.class)
    public ResponseEntity<UserErrorResponse> handleWrongUserPasswordException(WrongUserPasswordException ex) {
        UserErrorResponse errorResponse = new UserErrorResponse(HttpStatus.LOCKED.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.LOCKED);
    }

    @ExceptionHandler(NotValidNewPasswordException.class)
    public ResponseEntity<UserErrorResponse> handleNotValidNewPasswordException (NotValidNewPasswordException ex) {
        UserErrorResponse errorResponse = new UserErrorResponse(HttpStatus.NO_CONTENT.value(), ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }


}
