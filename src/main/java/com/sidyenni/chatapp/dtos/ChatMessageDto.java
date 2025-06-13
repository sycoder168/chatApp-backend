package com.sidyenni.chatapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private Long chatId;
    private Long senderId;
    private Long receiverId;
    private String message;
    private Long timestamp;
}
