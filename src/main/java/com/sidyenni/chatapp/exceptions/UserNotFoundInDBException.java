package com.sidyenni.chatapp.exceptions;

public class UserNotFoundInDBException extends Exception {
    public UserNotFoundInDBException(String message) {
        super(message);
    }
}
