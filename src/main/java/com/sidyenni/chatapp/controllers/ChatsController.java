package com.sidyenni.chatapp.controllers;

import com.sidyenni.chatapp.dtos.UserChatDto;
import com.sidyenni.chatapp.exceptions.NoUsersFoundInDBException;
import com.sidyenni.chatapp.exceptions.UserNotFoundInDBException;
import com.sidyenni.chatapp.services.chats.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatsController {

    private final ChatService chatService;

    public ChatsController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/user/chats/{userId}")
    public ResponseEntity<List<UserChatDto>> getAllChatsOfUser(@PathVariable Long userId) throws NoUsersFoundInDBException, UserNotFoundInDBException {
        List<UserChatDto> userChats = chatService.getAllChatsOfUser(userId);
        return ResponseEntity.ok(userChats);
    }

}
