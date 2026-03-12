package com.example.demo.service;

import com.example.demo.dto.NewsDTO;
import java.util.List;

public interface PostService {
    List<NewsDTO.NewsResponse> getPosts(int page, int size);
}
