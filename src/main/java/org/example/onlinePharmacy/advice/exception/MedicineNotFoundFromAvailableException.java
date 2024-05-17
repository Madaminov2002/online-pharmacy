package org.example.onlinePharmacy.advice.exception;

public class MedicineNotFoundFromAvailableException extends RuntimeException {
    public MedicineNotFoundFromAvailableException(Long id) {
        super("Medicine with id " + id + " not found from available");
    }
}