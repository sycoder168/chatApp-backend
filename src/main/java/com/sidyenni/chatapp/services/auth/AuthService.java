package com.sidyenni.chatapp.services.auth;

import com.sidyenni.chatapp.exceptions.IncorrectPasswordException;
import com.sidyenni.chatapp.exceptions.UserAlreadyExistsInDBException;
import com.sidyenni.chatapp.exceptions.UserNotFoundInDBException;
import com.sidyenni.chatapp.models.User;

public interface AuthService {
    User signup(String username, String password) throws UserAlreadyExistsInDBException;
    String login(String username, String password) throws UserNotFoundInDBException, IncorrectPasswordException;
}
