package com.sidyenni.chatapp.dtos;

import com.sidyenni.chatapp.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllUsersResponseDto {
    List<UserDto> allUsers;
    String message;
}
