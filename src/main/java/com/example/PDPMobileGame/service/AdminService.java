package com.example.PDPMobileGame.service;

import com.example.PDPMobileGame.entity.UserEntity;
import com.example.PDPMobileGame.exception.UserNotFoundException;
import com.example.PDPMobileGame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers () {
        return userRepository.findAll();
    }

    public ResponseEntity<UserEntity> getUsers (Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id:" + id + " not found.")
        );
        return ResponseEntity.ok(user);
    }
}
