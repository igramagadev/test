package com.example.demo.controller;

import com.example.demo.dto.NewsDTO;
import com.example.demo.entity.News;
import com.example.demo.repository.NewsRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Posts")
public class PostController {
    private final NewsRepository newsRepository;

    @GetMapping
    public List<NewsDTO.NewsResponse> getPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return newsRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size)).stream().map(this::toDto).toList();
    }

    private NewsDTO.NewsResponse toDto(News n) {
        NewsDTO.NewsResponse dto = new NewsDTO.NewsResponse();
        dto.setId(n.getId());
        dto.setTitle(n.getTitle());
        dto.setText(n.getText());
        dto.setImage_url(n.getImageUrl());
        dto.setCreated_at(n.getCreatedAt().toString());
        dto.setAuthor_id(n.getAuthor().getId());
        return dto;
    }
}
