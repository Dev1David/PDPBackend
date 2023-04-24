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
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/create")
    public void createNewUser (@RequestBody @Valid UserCreateRequest userCreateRequest) {
        try {
            System.out.println( "USER IS \n" + userCreateRequest);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser (
            @RequestBody @Valid UserCreateRequest userCreateRequest
    ) throws JsonProcessingException {
        String email = userCreateRequest.getEmail();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new UserNotFoundException("User with email: " + email + " not found.");
                });

        if (user != null) {
            String password = userCreateRequest.getPassword();
            System.out.println(password);
            boolean isCorrectPassword = passwordEncoder.matches(password, user.getPassword());

            if (!isCorrectPassword) {
                user.setLoginFailedAttempts(user.getLoginFailedAttempts() + 1);
                userRepository.save(user);
                throw new UserNotFoundException("Email or password is not correct");
            }

            String token = jwtTokenProvider.createToken();
            user.setToken(token);

            Map<String, Object> json = new HashMap<>();
            json.put("data", user);
            json.put("token", token);

            userRepository.save(user);
            return ResponseEntity.ok(json);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> checkUser(@RequestHeader("Authorization") String token) {
        String clearToken = token.substring(7);
        UserEntity user = userRepository.findByToken(clearToken);
        Map<String, Object> json = new HashMap<>();
        json.put("data", user);
        json.put("token", token);
        return ResponseEntity.ok(json);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid UserUpdateRequest userUpdateRequest
    ) {
        UserEntity user = userRepository.findByToken(token.substring(7));

        if (user == null) {
            throw new UserNotFoundException("User not found");
        } else {
            if (userUpdateRequest.getCurrentPassword() != null) {
                if (passwordEncoder.matches(userUpdateRequest.getCurrentPassword(), user.getPassword())) {
                    if (userUpdateRequest.getNewPassword() != null) {
                        user.setFirstName(userUpdateRequest.getFirstName());
                        user.setLastName(userUpdateRequest.getLastName());
                        user.setPassword(passwordEncoder.encode(userUpdateRequest.getNewPassword()));
                        userRepository.save(user);
                        return ResponseEntity.ok(user);
                    } else {
                        throw new NotValidNewPasswordException("New password should be valid");
                    }
                } else {
                    throw new WrongUserPasswordException("Current password is wrong");
                }
            }

            user.setFirstName(userUpdateRequest.getFirstName());
            user.setLastName(userUpdateRequest.getLastName());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }
    }
}
