package com.example.stomp.controller;

import com.example.stomp.domain.Message;
import com.example.stomp.domain.ResponseMessage;
import com.example.stomp.mapper.ChatRoomMapper;
import com.example.stomp.mapper.MessagesMapper;
import com.example.stomp.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {
    private final SimpMessagingTemplate template;

    @Autowired
    private MessagesMapper messagesMapper;

    @Autowired
    private ChatRoomMapper chatRoomMapper;

    public MessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/message")
    //@SendTo("/sub/messages")
    public ResponseMessage getMessage(Message message, @Header(value = "Authorization") String nickname){
        message.setSender(nickname);
        String receiverName = message.getReceiver();
        template.convertAndSend("/sub/messages/"+nickname, "나:"+message.getMessageContent());
        template.convertAndSend("/sub/messages/"+receiverName, message.getSender()+":"+message.getMessageContent());

        //메세지 db 저장
        messagesMapper.uploadMessage(message);

        //chatRoom message 업데이트
        chatRoomMapper.updateChatText(message);
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }
}
