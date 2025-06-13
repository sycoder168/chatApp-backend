package com.sidyenni.chatapp.exceptions;

public class TokenValidationFailedException extends RuntimeException {
  public TokenValidationFailedException(String message) {
    super(message);
  }
}
