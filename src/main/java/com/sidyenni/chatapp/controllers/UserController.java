package com.sidyenni.chatapp.controllers;

import com.sidyenni.chatapp.dtos.GetAllUsersResponseDto;
import com.sidyenni.chatapp.dtos.UserDto;
import com.sidyenni.chatapp.exceptions.NoUsersFoundInDBException;
import com.sidyenni.chatapp.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<GetAllUsersResponseDto> getAllUsers() throws NoUsersFoundInDBException {
        List<UserDto> allUsers = userService.getAllUsersUserIdAndUserName();
        GetAllUsersResponseDto getAllUsersResponseDto = new GetAllUsersResponseDto();
        getAllUsersResponseDto.setAllUsers(allUsers);
        getAllUsersResponseDto.setMessage("success");
        return ResponseEntity.ok(getAllUsersResponseDto);
    }
}
