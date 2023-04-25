package com.example.PDPMobileGame.controller;
import com.example.PDPMobileGame.DTO.UserCreateRequest;
import com.example.PDPMobileGame.DTO.UserUpdateRequest;
import com.example.PDPMobileGame.entity.RoleEntity;
import com.example.PDPMobileGame.entity.UserEntity;
import com.example.PDPMobileGame.exception.NotValidNewPasswordException;
import com.example.PDPMobileGame.exception.UserNotFoundException;
import com.example.PDPMobileGame.exception.WrongUserPasswordException;
import com.example.PDPMobileGame.repository.RoleRepository;
import com.example.PDPMobileGame.repository.UserRepository;
import com.example.PDPMobileGame.security.JwtTokenProvider;
import com.example.PDPMobileGame.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JavaMailSender javaMailSender;

    UserService userService = new UserService();

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }


    @PostMapping("/create")
    public void createNewUser (@RequestBody @Valid UserCreateRequest userCreateRequest) {
       userService.createNewUser(userCreateRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser (
            @RequestBody @Valid UserCreateRequest userCreateRequest
    ) throws JsonProcessingException {
        return userService.loginUser(userCreateRequest);
    }

    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> checkUser(@RequestHeader("Authorization") String token) {
        return userService.checkUser(token);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid UserUpdateRequest userUpdateRequest
    ) {
        return userService.updateUser(token, userUpdateRequest);
    }
}
