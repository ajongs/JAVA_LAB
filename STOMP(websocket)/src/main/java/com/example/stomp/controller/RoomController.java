/*
package com.example.stomp.controller;

import com.example.stomp.repository.ChatRoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoomController {

    private final ChatRoomRepository repository;

    public RoomController(ChatRoomRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/rooms")
    public ResponseEntity rooms(){
        return new ResponseEntity(repository.findAllRooms(), HttpStatus.OK);
    }

    @PostMapping(value = "/room")
    public String create(@RequestParam String name){
        repository.createChatRoom(name);
        return "채팅방이 개설되었습니다.";
    }
    @GetMapping("/room")
    public void getRoom(String roomId){
        repository.findRoomById(roomId);
    }
}
*/