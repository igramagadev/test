package com.example.demo.service.impl;

import com.example.demo.dto.ChatDTO;
import com.example.demo.entity.Chat;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.ChatService;
import com.example.demo.service.CurrentUserService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final CurrentUserService currentUserService;

    @Override
    public List<ChatDTO.MessageResponse> getMessages(int page, int size) {
        return messageRepository.findAllByOrderBySentAtDesc(PageRequest.of(page, size)).stream().map(this::toDto).toList();
    }

    @Override
    public ChatDTO.MessageResponse send(ChatDTO.MessageRequest request) {
        User user = currentUserService.getCurrentUser();
        Chat chat = chatRepository.findById(request.getChatId()).orElseThrow();
        Message message = new Message();
        message.setText(request.getText());
        message.setUser(user);
        message.setChat(chat);
        return toDto(messageRepository.save(message));
    }

    @Override
    public ChatDTO.MessageResponse edit(Integer id, ChatDTO.MessageRequest request) {
        User user = currentUserService.getCurrentUser();
        Message message = messageRepository.findById(id).orElseThrow();
        if (!message.getUser().getId().equals(user.getId())) throw new BadRequestException("Only author can edit message");
        message.setText(request.getText());
        message.setSentAt(LocalDateTime.now());
        return toDto(messageRepository.save(message));
    }

    @Override
    public void delete(Integer id) {
        User user = currentUserService.getCurrentUser();
        Message message = messageRepository.findById(id).orElseThrow();
        if (!message.getUser().getId().equals(user.getId())) throw new BadRequestException("Only author can delete message");
        messageRepository.delete(message);
    }

    private ChatDTO.MessageResponse toDto(Message message) {
        ChatDTO.MessageResponse dto = new ChatDTO.MessageResponse();
        dto.setId(message.getId()); dto.setText(message.getText()); dto.setSent_at(message.getSentAt().toString()); dto.setUser_id(message.getUser().getId()); dto.setChat_id(message.getChat().getId());
        return dto;
    }
}
