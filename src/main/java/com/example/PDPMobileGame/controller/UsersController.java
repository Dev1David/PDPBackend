package com.example.PDPMobileGame.controller;
import com.example.PDPMobileGame.entity.UserEntity;
import com.example.PDPMobileGame.repository.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<String> getAllUsers () throws Exception {
//        try {
//            return passwordEncoder.encode("PASSWORD");
//        } catch (Exception err) {
//            throw new Exception(err);
//        }
        return ResponseEntity.ok(userRepository.findAll().toString());
    }

    @PostMapping
    private String createNewUser () {
        return "";
    }
}
