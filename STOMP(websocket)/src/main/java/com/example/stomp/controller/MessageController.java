package com.example.stomp.controller;

import com.example.stomp.domain.Message;
import com.example.stomp.domain.ResponseMessage;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {
    private final SimpMessagingTemplate template;

    public MessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/message")
    //@SendTo("/sub/messages")
    public ResponseMessage getMessage(Message message, @Header(value = "Authorization") String nickname){
        message.setSender(nickname);
        template.convertAndSend("/sub/messages/"+nickname, "나 : "+message.getMessageContent());
        template.convertAndSend("/sub/messages/"+message.getReceiver(), message.getSender()+" : "+message.getMessageContent());
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }
}
