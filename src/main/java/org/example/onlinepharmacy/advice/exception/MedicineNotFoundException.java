package org.example.onlinepharmacy.advice.exception;

public class MedicineNotFoundException extends RuntimeException {
    public MedicineNotFoundException(Long id) {
        super("Medicine with id " + id + " not found");
    }
}
