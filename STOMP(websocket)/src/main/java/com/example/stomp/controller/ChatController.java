/*
package com.example.stomp.controller;

import com.example.stomp.domain.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private final SimpMessagingTemplate template;

    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    //prefix 포함하면 pub/chat/enter
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessage chatMessage){
        chatMessage.setMessage(chatMessage.getName()+"님이 입장하였습니다.");
        template.convertAndSend("/sub/chat/room/"+chatMessage.getRoomId(), chatMessage);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessage chatMessage){
        template.convertAndSend("/sub/chat/room/"+chatMessage.getRoomId(), chatMessage);
    }
}
*/