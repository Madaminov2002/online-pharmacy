package org.example.onlinepharmacy.advice.exception;

public class DistrictNotFoundException extends RuntimeException {
    public DistrictNotFoundException(String param) {
        super("District not found with param [" + param + "]");
    }
}
