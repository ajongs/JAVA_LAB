package com.example.stomp.mapper;

import com.example.stomp.domain.ChatRoom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChatRoomMapper {
    void createChatRoom(ChatRoom chatRoom);
    List<ChatRoom> findAllChatRoom(String userNickname);
}
