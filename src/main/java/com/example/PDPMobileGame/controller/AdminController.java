package com.example.PDPMobileGame.controller;

import com.example.PDPMobileGame.entity.UserEntity;
import com.example.PDPMobileGame.exception.UserNotFoundException;
import com.example.PDPMobileGame.repository.UserRepository;
import com.example.PDPMobileGame.service.AdminService;
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

    AdminService adminService = new AdminService();


    @GetMapping("/users")
    public List<UserEntity> getAllUsers () {
        return adminService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserEntity> getUsers (@PathVariable Long id) {
        return adminService.getUsers(id);
    }
}
