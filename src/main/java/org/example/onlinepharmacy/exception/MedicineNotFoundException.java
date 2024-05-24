package org.example.onlinepharmacy.exception;

public class MedicineNotFoundException extends RuntimeException {
    public MedicineNotFoundException(Long id) {
        super("Medicine with id " + id + " not found");
    }
}
