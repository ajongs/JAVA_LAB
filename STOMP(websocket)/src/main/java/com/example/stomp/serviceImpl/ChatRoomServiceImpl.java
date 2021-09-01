package com.example.stomp.serviceImpl;

import com.example.stomp.domain.ChatRoom;
import com.example.stomp.mapper.ChatRoomMapper;
import com.example.stomp.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    @Autowired
    private ChatRoomMapper chatRoomMapper;
    @Override
    public void createChatRoom(ChatRoom chatRoom) {
        String user1 = getLoginUser();
        chatRoom.setUser1(user1);

        chatRoomMapper.createChatRoom(chatRoom);
    }

    @Override
    public List<ChatRoom> findAllChatRoom() {
        String userNickname = getLoginUser();
        return chatRoomMapper.findAllChatRoom(userNickname);
    }

    private String getLoginUser(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }
}
