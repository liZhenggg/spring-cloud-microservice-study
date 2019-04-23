package com.lzg.cloud.study.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/movies")
@RestController
public class MovieController {

    @GetMapping("/users/{id}")
    public void findById(@PathVariable Long id) {
        System.out.println("---->>test MovieController >> findById()");
    }
}