package com.example.stomp.mapper;

import com.example.stomp.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MessagesMapper {
    void uploadMessage(Message message);
}
