package com.example.stomp.controller;

import com.example.stomp.domain.Message;
import com.example.stomp.domain.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {
    @MessageMapping("/message")
    @SendTo("/sub/messages")
    public ResponseMessage getMessage(Message message){
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }
}
