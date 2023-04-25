package com.example.PDPMobileGame.service;

import com.example.PDPMobileGame.DTO.UserCreateRequest;
import com.example.PDPMobileGame.DTO.UserUpdateRequest;
import com.example.PDPMobileGame.entity.UserEntity;
import com.example.PDPMobileGame.exception.NotValidNewPasswordException;
import com.example.PDPMobileGame.exception.UserNotFoundException;
import com.example.PDPMobileGame.exception.WrongUserPasswordException;
import com.example.PDPMobileGame.repository.RoleRepository;
import com.example.PDPMobileGame.repository.UserRepository;
import com.example.PDPMobileGame.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> loginUser (UserCreateRequest userCreateRequest) {
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


    public void createNewUser (UserCreateRequest userCreateRequest) {
        try {
            System.out.println( "USER IS \n" + userCreateRequest);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public ResponseEntity<Map<String, Object>> checkUser (String token) {
        String clearToken = token.substring(7);
        UserEntity user = userRepository.findByToken(clearToken);
        Map<String, Object> json = new HashMap<>();
        json.put("data", user);
        json.put("token", token);
        return ResponseEntity.ok(json);
    }

    public ResponseEntity<?> updateUser(
            String token,
            UserUpdateRequest userUpdateRequest
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
