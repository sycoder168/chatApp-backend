package com.sidyenni.chatapp.exceptions;

public class UserAlreadyExistsInDBException extends Exception {
    public UserAlreadyExistsInDBException(String message) {
        super(message);
    }
}
