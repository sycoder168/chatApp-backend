//package com.sidyenni.chatapp;
//
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class ChatController {
//
//    @MessageMapping("/chat/{roomId}")
//    @SendTo("/topic/chat/{roomId}")
//    public ChatMessage sendMessage(@DestinationVariable String roomId, ChatMessage message) {
//        System.out.println("Received message: " + message.getMessage());
//        return message;
//    }
//}
