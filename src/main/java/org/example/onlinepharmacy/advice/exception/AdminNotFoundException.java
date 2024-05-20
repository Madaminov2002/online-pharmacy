package org.example.onlinepharmacy.advice.exception;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(Long adminId) {
        super("Admin with id " + adminId + " not found");
    }
}
