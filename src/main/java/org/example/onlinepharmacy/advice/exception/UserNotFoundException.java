package org.example.onlinepharmacy.advice.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String param) {
        super("User not found by [" + param + "]");
    }
}
