package org.example.onlinePharmacy.advice.exception;

public class IsNotEnoughMoneyException extends RuntimeException {
    public IsNotEnoughMoneyException() {
        super("Not enough money in your card!");
    }
}