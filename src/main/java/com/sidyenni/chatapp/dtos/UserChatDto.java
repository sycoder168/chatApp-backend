package com.sidyenni.chatapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserChatDto {
    private UserDto user;
    private Long chatId;
    private List<MessageDto> messages;
}
