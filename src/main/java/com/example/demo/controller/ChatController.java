package com.example.demo.controller;

import com.example.demo.dto.ChatDTO;
import com.example.demo.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/messages")
@RequiredArgsConstructor
@Tag(name = "Chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping
    public List<ChatDTO.MessageResponse> getMessages(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) { return chatService.getMessages(page, size); }

    @PostMapping
    public ChatDTO.MessageResponse sendMessage(@Valid @RequestBody ChatDTO.MessageRequest request) { return chatService.send(request); }

    @PutMapping("/{messageId}")
    public ChatDTO.MessageResponse editMessage(@PathVariable Integer messageId, @Valid @RequestBody ChatDTO.MessageRequest request) { return chatService.edit(messageId, request); }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(@PathVariable Integer messageId) { chatService.delete(messageId); }
}
