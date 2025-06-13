package com.sidyenni.chatapp.controllers;

import com.sidyenni.chatapp.dtos.LoginRequestDto;
import com.sidyenni.chatapp.dtos.LoginResponseDto;
import com.sidyenni.chatapp.dtos.SignupRequestDto;
import com.sidyenni.chatapp.dtos.SignupResponseDto;
import com.sidyenni.chatapp.exceptions.IncorrectPasswordException;
import com.sidyenni.chatapp.exceptions.UserAlreadyExistsInDBException;
import com.sidyenni.chatapp.exceptions.UserNotFoundInDBException;
import com.sidyenni.chatapp.models.User;
import com.sidyenni.chatapp.services.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto requestDto) throws UserAlreadyExistsInDBException {
        User user = authService.signup(requestDto.getUsername(), requestDto.getPassword());

        SignupResponseDto responseDto = new SignupResponseDto();
        responseDto.setMessage("Signup Successful! Proceed to Login");

        return new  ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) throws UserNotFoundInDBException, IncorrectPasswordException {
        String token = authService.login(requestDto.getUsername(), requestDto.getPassword());
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setMessage("Login Successful!");
        responseDto.setToken(token);
        return new  ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
