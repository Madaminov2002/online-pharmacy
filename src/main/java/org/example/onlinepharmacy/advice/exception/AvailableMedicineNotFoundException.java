package org.example.onlinepharmacy.advice.exception;

public class AvailableMedicineNotFoundException extends RuntimeException {
    public AvailableMedicineNotFoundException(Long id) {
        super("Available Medicine with id " + id + " not found");
    }
}
