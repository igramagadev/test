package com.example.demo.controller;

import com.example.demo.dto.ChatDtos.*;
import com.example.demo.entity.ChatMessage;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.service.CurrentUserService;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/messages")
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageRepository repo;
    private final CurrentUserService currentUserService;

    @GetMapping
    public List<ChatMessageResponse> list(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size){
        return repo.findAllByDeletedFalse(PageRequest.of(page,size)).stream().map(this::map).toList();
    }
    @PostMapping
    public ChatMessageResponse create(@Valid @RequestBody CreateMessageRequest req){
        User u = currentUserService.current();
        ChatMessage m = new ChatMessage(); m.setAuthor(u); m.setText(req.text());
        return map(repo.save(m));
    }
    @PutMapping("/{id}")
    public ChatMessageResponse update(@PathVariable Long id,@Valid @RequestBody UpdateMessageRequest req){
        User u = currentUserService.current();
        ChatMessage m = repo.findById(id).orElseThrow(() -> new NotFoundException("Message not found"));
        if (!m.getAuthor().getId().equals(u.getId())) throw new BadRequestException("No rights");
        m.setText(req.text()); m.setUpdatedAt(Instant.now());
        return map(repo.save(m));
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        User u = currentUserService.current();
        ChatMessage m = repo.findById(id).orElseThrow(() -> new NotFoundException("Message not found"));
        if (!m.getAuthor().getId().equals(u.getId())) throw new BadRequestException("No rights");
        m.setDeleted(true); repo.save(m);
    }

    private ChatMessageResponse map(ChatMessage m){ return new ChatMessageResponse(m.getId(),m.getAuthor().getId(),m.getAuthor().getUsername(),m.getText(),m.getCreatedAt(),m.getUpdatedAt()); }
}
