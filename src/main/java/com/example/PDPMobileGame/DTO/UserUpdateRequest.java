package com.example.PDPMobileGame.DTO;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserUpdateRequest {


    @Size(min = 1, message = "Min length = 1")
    private String firstName;

    @Size(min = 1, message = "Min length = 1")
    private String lastName;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,20}$", message = "Password should contain at least 1 big chars and 1 number, and length should be between 6-20 chars")
    private String currentPassword;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,20}$", message = "Password should contain at least 1 big chars and 1 number, and length should be between 6-20 chars")
    private String newPassword;

    public UserUpdateRequest() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UserUpdateRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
