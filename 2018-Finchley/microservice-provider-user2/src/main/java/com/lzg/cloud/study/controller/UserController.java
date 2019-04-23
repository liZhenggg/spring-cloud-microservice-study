package com.lzg.cloud.study.controller;

import com.lzg.cloud.study.entity.User;
import com.lzg.cloud.study.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id) {
        System.out.println("---->> UserController >> findById()");
        return this.userRepository.findById(id);
    }

}
