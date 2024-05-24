package org.example.onlinepharmacy.exception;

public class UserNotEnableForChangingPasswordException extends RuntimeException {
    public UserNotEnableForChangingPasswordException(String email) {
        super(String.format("User with email %s is not enabled for changing password", email));
    }
}
