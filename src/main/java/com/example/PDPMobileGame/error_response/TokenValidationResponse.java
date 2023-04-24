package com.example.PDPMobileGame.error_response;

import org.springframework.http.ResponseEntity;

public class TokenValidationResponse {
    private boolean isValid;
    private ResponseEntity<Object> errorResponse;

    public TokenValidationResponse(boolean isValid) {
        this.isValid = isValid;
    }

    public TokenValidationResponse (ResponseEntity<Object> errorResponse) {
        this.isValid = false;
        this.errorResponse = errorResponse;
    }

    public boolean isValid() {
        return isValid;
    }

    public ResponseEntity<Object> getErrorResponse() {
        return errorResponse;
    }
}
