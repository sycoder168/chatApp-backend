package com.sidyenni.chatapp.services.chats;

import com.sidyenni.chatapp.dtos.MessageDto;
import com.sidyenni.chatapp.dtos.UserChatDto;
import com.sidyenni.chatapp.dtos.UserDto;
import com.sidyenni.chatapp.exceptions.NoUsersFoundInDBException;
import com.sidyenni.chatapp.exceptions.UserNotFoundInDBException;
import com.sidyenni.chatapp.models.Chat;
import com.sidyenni.chatapp.models.Message;
import com.sidyenni.chatapp.models.MessageType;
import com.sidyenni.chatapp.models.User;
import com.sidyenni.chatapp.repositories.ChatRepository;
import com.sidyenni.chatapp.repositories.MessageRepository;
import com.sidyenni.chatapp.repositories.UserRepository;
import com.sidyenni.chatapp.services.user.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final UserService userService;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ChatServiceImpl(UserService userService, ChatRepository chatRepository,
                           MessageRepository messageRepository, UserRepository userRepository) {
        this.userService = userService;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserChatDto> getAllChatsOfUser(Long userId) throws NoUsersFoundInDBException, UserNotFoundInDBException {
        List<User> allUsers = userService.getAllUsers();
        User mainUser = userService.getUserById(userId);

        List<UserChatDto> userChatDtos = new ArrayList<>();

        for (User user : allUsers) {
            Chat chat =
                    chatRepository.findChatBetweenUsers(user.getId(), userId).orElseGet(() -> {
                        Chat newChat = new Chat();
                        newChat.setUser1(mainUser);
                        newChat.setUser2(user);
                        return chatRepository.save(newChat);
                    });

            List<Message> messages = messageRepository.findAllByChat_Id(chat.getId());
            UserChatDto userChatDto = getUserChatDto(messages, chat, user);
            userChatDtos.add(userChatDto);
        }

        return userChatDtos;
    }

    private static UserChatDto getUserChatDto(List<Message> messages, Chat chat, User user) {
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messages) {
            MessageDto messageDto = new MessageDto();
            messageDto.setChatId(chat.getId());
            messageDto.setMessageId(message.getId());
            messageDto.setContent(message.getContent());
            messageDto.setSeen(message.getSeen());
            messageDto.setSenderId(message.getSender().getId());
            messageDtos.add(messageDto);
        }
        UserChatDto userChatDto = new UserChatDto();
        userChatDto.setUser(new UserDto(user.getId(), user.getUsername()));
        userChatDto.setMessages(messageDtos);
        userChatDto.setChatId(chat.getId());
        return userChatDto;
    }

    @Override
    public MessageDto saveChatMessage(Long chatId, Long senderId, String content, Long timestamp) {
        Chat chat = chatRepository.findById(chatId).get();
        Message message = new Message();
        message.setChat(chat);
        message.setType(MessageType.TEXT);
        message.setSender(userRepository.findById(senderId).get());
        message.setTimestamp(timestamp);
        message.setSeen(false);
        message.setContent(content);

        Message savedMessage = messageRepository.save(message);

        MessageDto messageDto = new MessageDto();
        messageDto.setChatId(chat.getId());
        messageDto.setMessageId(savedMessage.getId());
        messageDto.setContent(savedMessage.getContent());
        messageDto.setSeen(savedMessage.getSeen());
        messageDto.setSenderId(savedMessage.getSender().getId());

        return messageDto;
    }
}
