package com.sidyenni.chatapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String message;
    private String token;
}
