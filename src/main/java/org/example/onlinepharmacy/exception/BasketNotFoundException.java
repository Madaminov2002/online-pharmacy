package org.example.onlinepharmacy.exception;

public class BasketNotFoundException extends RuntimeException{
    public BasketNotFoundException() {
        super("Basket not found with id ");
    }
}
