package com.example.PDPMobileGame.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;


public class UserCreateRequest {
    @Email(message = "Invalid email address")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,20}$", message = "Password should contain at least 1 big chars and 1 number, and length should be between 6-20 chars")
    private String password;

    public UserCreateRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserCreateRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
