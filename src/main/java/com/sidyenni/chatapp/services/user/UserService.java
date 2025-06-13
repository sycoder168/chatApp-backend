package com.sidyenni.chatapp.services.user;


import com.sidyenni.chatapp.dtos.UserDto;
import com.sidyenni.chatapp.exceptions.NoUsersFoundInDBException;
import com.sidyenni.chatapp.exceptions.UserNotFoundInDBException;
import com.sidyenni.chatapp.models.User;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsersUserIdAndUserName() throws NoUsersFoundInDBException;
    List<User> getAllUsers() throws NoUsersFoundInDBException;
    User getUserById(Long id) throws UserNotFoundInDBException;
}
