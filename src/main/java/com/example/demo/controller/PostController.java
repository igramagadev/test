package com.example.demo.controller;

import com.example.demo.dto.NewsDTO;
import com.example.demo.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public List<NewsDTO.NewsResponse> getPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return postService.getPosts(page, size);
    }
}
