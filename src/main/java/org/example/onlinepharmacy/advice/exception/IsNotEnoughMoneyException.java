package org.example.onlinepharmacy.advice.exception;

public class IsNotEnoughMoneyException extends RuntimeException {
    public IsNotEnoughMoneyException() {
        super("Not enough money in your card!");
    }
}
