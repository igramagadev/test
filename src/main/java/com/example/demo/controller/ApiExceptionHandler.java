package com.example.demo.controller;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> nf(NotFoundException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    @ExceptionHandler({BadRequestException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class, IllegalArgumentException.class})
    public ResponseEntity<String> bad(Exception e){ return ResponseEntity.badRequest().body(e.getMessage()); }
}
