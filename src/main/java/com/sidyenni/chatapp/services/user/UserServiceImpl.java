package com.sidyenni.chatapp.services.user;

import com.sidyenni.chatapp.dtos.UserDto;
import com.sidyenni.chatapp.exceptions.NoUsersFoundInDBException;
import com.sidyenni.chatapp.exceptions.UserNotFoundInDBException;
import com.sidyenni.chatapp.models.User;
import com.sidyenni.chatapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsersUserIdAndUserName() throws NoUsersFoundInDBException {
        List<UserDto> userDtos = new ArrayList<>();

        if (userRepository.findAll().isEmpty()) {
            throw new NoUsersFoundInDBException("No Users found in database");
        }

        for (User user : userRepository.findAll()) {
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getId());
            userDto.setUsername(user.getUsername());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundInDBException {
        return  userRepository.findById(id).orElseThrow(() -> new UserNotFoundInDBException("User not found"));
    }

    @Override
    public List<User> getAllUsers() throws NoUsersFoundInDBException {
        Iterable<User> users = userRepository.findAll();
        List<User> allUsers = new ArrayList<>();
        for (User user : users) {
            allUsers.add(user);
        }

        return allUsers;
    }


}
