package com.example.demo.repository;

import com.example.demo.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
    Page<News> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
