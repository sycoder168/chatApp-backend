package com.sidyenni.chatapp.services.chats;

import com.sidyenni.chatapp.dtos.MessageDto;
import com.sidyenni.chatapp.dtos.UserChatDto;
import com.sidyenni.chatapp.exceptions.NoUsersFoundInDBException;
import com.sidyenni.chatapp.exceptions.UserNotFoundInDBException;

import java.util.List;

public interface ChatService {
    List<UserChatDto> getAllChatsOfUser(Long userId) throws NoUsersFoundInDBException, UserNotFoundInDBException;
    MessageDto saveChatMessage(Long chatId, Long senderId, String message, Long timestamp);
}
