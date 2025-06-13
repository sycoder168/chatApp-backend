package com.sidyenni.chatapp.controllers;

import com.sidyenni.chatapp.dtos.ChatMessageDto;
import com.sidyenni.chatapp.dtos.MessageDto;
import com.sidyenni.chatapp.services.chats.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatCommunicationController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    public ChatCommunicationController(SimpMessagingTemplate simpMessagingTemplate,
                                       ChatService chatService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatService = chatService;
    }

    @MessageMapping("/chat")
    public void handleChat(@Payload ChatMessageDto chatMessageDto) {
        MessageDto messageDto = chatService.saveChatMessage(chatMessageDto.getChatId(),
                                                                 chatMessageDto.getSenderId(),
                                                                 chatMessageDto.getMessage(),
                                                                 chatMessageDto.getTimestamp());

        simpMessagingTemplate.convertAndSendToUser(chatMessageDto.getReceiverId().toString(), "/queue" +
                "/messages", messageDto);
    }
}
