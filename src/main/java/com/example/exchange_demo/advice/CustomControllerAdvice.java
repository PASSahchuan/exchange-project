package com.example.exchange_demo.advice;

import com.example.exchange_demo.advice.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.example.exchange_demo.controller")
public class CustomControllerAdvice {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<Map<String, Object>> handleSelfThrowException(CustomException e) {
    Map<String, Object> result = new HashMap<>();
    result.put("msg", e.getMessage());
    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleVaildFaileThrowException(MethodArgumentNotValidException e) {
    List<String> errors =
        e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.toList());
    Map<String, Object> result = new HashMap<>();
    result.put("msg", String.join(",", errors));
    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleUnknowException(Exception e) {
    Map<String, Object> result = new HashMap<>();
    result.put("msg", e.getMessage());
    return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}


