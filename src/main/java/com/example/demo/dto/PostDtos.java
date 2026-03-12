package com.example.demo.dto;

import java.time.Instant;

public class PostDtos { public record PostResponse(Long id,String title,String content,Instant createdAt){} }
