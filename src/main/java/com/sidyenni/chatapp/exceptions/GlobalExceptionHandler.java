package com.sidyenni.chatapp.exceptions;

import com.sidyenni.chatapp.dtos.GetAllUsersResponseDto;
import com.sidyenni.chatapp.dtos.LoginResponseDto;
import com.sidyenni.chatapp.dtos.SignupResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsInDBException.class)
    public ResponseEntity<SignupResponseDto> handleUserAlreadyExistsInDBException() {
        SignupResponseDto signupResponseDto = new SignupResponseDto();
        signupResponseDto.setMessage("User already exists in database");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(signupResponseDto);
    }

    @ExceptionHandler(UserNotFoundInDBException.class)
    public ResponseEntity<LoginResponseDto> handleUserNotFoundException() {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setMessage("User not found");
        loginResponseDto.setToken("");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(loginResponseDto);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<LoginResponseDto> handleIncorrectPasswordException() {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setMessage("Incorrect Password");
        loginResponseDto.setToken("");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponseDto);
    }

    @ExceptionHandler(NoUsersFoundInDBException.class)
    public ResponseEntity<GetAllUsersResponseDto> handleNoUsersFoundInDBException() {
        GetAllUsersResponseDto getAllUsersResponseDto = new GetAllUsersResponseDto();
        getAllUsersResponseDto.setAllUsers(new ArrayList<>());
        getAllUsersResponseDto.setMessage("No users found in database");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getAllUsersResponseDto);
    }
}
