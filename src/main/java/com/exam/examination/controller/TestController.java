package com.exam.examination.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping
    public ResponseEntity<String> getHelloWorld() {

        String hello = "Hello, World!";

        return ResponseEntity.status(HttpStatus.FOUND).body(hello);

    }
}
