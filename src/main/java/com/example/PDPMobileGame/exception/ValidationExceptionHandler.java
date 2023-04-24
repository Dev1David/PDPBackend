package com.example.PDPMobileGame.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {

    ObjectMapper objectMapper = new ObjectMapper();

    public String mapToJsonString(Map<String, Object> data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationException(ConstraintViolationException e) throws JsonProcessingException {
        Map<String, Object> errors = new HashMap<>();
        errors.put("errors", e.getMessage());

        return ResponseEntity.badRequest().body(mapToJsonString(errors));
    }
}
