package com.example.demo.service.impl;

import com.example.demo.dto.NewsDTO;
import com.example.demo.entity.News;
import com.example.demo.repository.NewsRepository;
import com.example.demo.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final NewsRepository newsRepository;

    @Override
    public List<NewsDTO.NewsResponse> getPosts(int page, int size) {
        return newsRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size)).stream().map(this::toDto).toList();
    }

    private NewsDTO.NewsResponse toDto(News n) {
        NewsDTO.NewsResponse dto = new NewsDTO.NewsResponse();
        dto.setId(n.getId()); dto.setTitle(n.getTitle()); dto.setText(n.getText()); dto.setImage_url(n.getImageUrl()); dto.setCreated_at(n.getCreatedAt().toString()); dto.setAuthor_id(n.getAuthor().getId());
        return dto;
    }
}
