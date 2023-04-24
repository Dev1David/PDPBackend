package com.example.PDPMobileGame.controller;

import com.example.PDPMobileGame.DTO.SpinRouletteRequest;
import com.example.PDPMobileGame.entity.UserEntity;
import com.example.PDPMobileGame.exception.NotEnoughFundsException;
import com.example.PDPMobileGame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;


@Validated
@RestController
@RequestMapping("/api/v1/roulette")
public class RouletteController {
    @Autowired
    UserRepository userRepository;
    @PostMapping
    public ResponseEntity<?> spinRouletteWithPercentage (
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody SpinRouletteRequest spinRouletteRequest
    ) {
        String currentToken = token.substring(7);
        UserEntity user = userRepository.findByToken(currentToken);

        if (user.getBalance() < spinRouletteRequest.getBetBalance()) {
            throw new NotEnoughFundsException("There are not enough funds in the account");
        }

        Integer currentBet = spinRouletteRequest.getBetBalance();
        Integer currentPercentage = spinRouletteRequest.getPercentage();

        Integer result = (int)Math.ceil(currentBet * currentPercentage / 100);

        user.setBalance(user.getBalance() + result);
        userRepository.save(user);

        HashMap<String, Object> response = new HashMap<>();
        response.put("result", result);
        response.put("currentBalance", user.getBalance());

        return ResponseEntity.ok(response);
    }
}
