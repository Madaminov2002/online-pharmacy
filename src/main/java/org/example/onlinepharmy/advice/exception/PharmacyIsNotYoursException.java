package org.example.onlinepharmy.advice.exception;

public class PharmacyIsNotYoursException extends RuntimeException {
    public PharmacyIsNotYoursException() {
        super("The pharmacy is not yours");
    }
}
