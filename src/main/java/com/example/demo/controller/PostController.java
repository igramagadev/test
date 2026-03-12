package com.example.demo.controller;

import com.example.demo.dto.PostDtos.PostResponse;
import com.example.demo.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    @GetMapping
    public List<PostResponse> list(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size){
        return postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page,size)).stream().map(p -> new PostResponse(p.getId(),p.getTitle(),p.getContent(),p.getCreatedAt())).toList();
    }
}
