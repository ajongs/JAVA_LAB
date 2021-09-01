package com.example.stomp.repository;

import com.example.stomp.domain.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init(){
        chatRoomMap= new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRooms(String nickname){
        List<ChatRoom> result = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(result);

        return result;
    }
    public ChatRoom findRoomById(String id){
        return chatRoomMap.get(id);
    }
    public ChatRoom createChatRoom(String name, String user1, String user2){
        //ChatRoom room = ChatRoom.create(name, user1, user2);
        //chatRoomMap.put(room.getRoomId(), room);

        //return room;
        return null;
    }
}
