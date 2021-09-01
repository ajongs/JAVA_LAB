package com.example.stomp.service;

import com.example.stomp.domain.ChatRoom;

import java.util.List;

public interface ChatRoomService {

    void createChatRoom(ChatRoom chatRoom);
    List<ChatRoom> findAllChatRoom();
}
