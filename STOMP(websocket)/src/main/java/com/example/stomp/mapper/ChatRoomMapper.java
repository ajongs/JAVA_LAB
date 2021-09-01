package com.example.stomp.mapper;

import com.example.stomp.domain.ChatRoom;
import com.example.stomp.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChatRoomMapper {
    void createChatRoom(ChatRoom chatRoom);
    List<ChatRoom> findAllChatRoom(String userNickname);
    void updateChatText(Message message);
}
