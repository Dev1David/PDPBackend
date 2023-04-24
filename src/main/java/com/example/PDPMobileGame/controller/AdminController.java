package com.example.PDPMobileGame.controller;

import com.example.PDPMobileGame.entity.UserEntity;
import com.example.PDPMobileGame.exception.UserNotFoundException;
import com.example.PDPMobileGame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<UserEntity> getAllUsers () {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserEntity> getUsers (@PathVariable Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id:" + id + " not found.")
        );
        return ResponseEntity.ok(user);
    }
}
