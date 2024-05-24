package org.example.onlinepharmacy.exception;

public class DistrictNotFoundException extends RuntimeException {
    public DistrictNotFoundException(String param) {
        super("District not found with param [" + param + "]");
    }
}
