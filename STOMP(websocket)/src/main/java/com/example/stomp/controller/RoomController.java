package com.example.stomp.controller;

import com.example.stomp.domain.ChatRoom;
import com.example.stomp.repository.ChatRoomRepository;
import com.example.stomp.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoomController {

    private final ChatRoomRepository repository;

    @Autowired
    private ChatRoomService chatRoomService;

    public RoomController(ChatRoomRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/rooms")
    public ResponseEntity rooms(){
        return new ResponseEntity(chatRoomService.findAllChatRoom(), HttpStatus.OK);
    }

    @PostMapping(value = "/room")
    public ResponseEntity create(@RequestBody ChatRoom chatRoom){
        return new ResponseEntity(chatRoomService.createChatRoom(chatRoom), HttpStatus.OK);
    }
    @GetMapping("/room")
    public void getRoom(String roomId){
        repository.findRoomById(roomId);
    }
}
