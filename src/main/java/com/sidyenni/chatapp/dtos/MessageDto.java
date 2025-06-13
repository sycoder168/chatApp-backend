package com.sidyenni.chatapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private Long messageId;
    private Long senderId;
    private Long chatId;
    private String content;
    private boolean seen;
}
