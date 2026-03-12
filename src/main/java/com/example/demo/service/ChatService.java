package com.example.demo.service;

import com.example.demo.dto.ChatDTO;
import java.util.List;

public interface ChatService {
    List<ChatDTO.MessageResponse> getMessages(int page, int size);
    ChatDTO.MessageResponse send(ChatDTO.MessageRequest request);
    ChatDTO.MessageResponse edit(Integer id, ChatDTO.MessageRequest request);
    void delete(Integer id);
}
