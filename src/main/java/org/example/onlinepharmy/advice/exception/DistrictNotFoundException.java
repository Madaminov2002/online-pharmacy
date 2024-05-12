package org.example.onlinepharmy.advice.exception;

public class DistrictNotFoundException extends RuntimeException {
    public DistrictNotFoundException(Long id) {
        super("District not found with id ["+id+"]");
    }
}
