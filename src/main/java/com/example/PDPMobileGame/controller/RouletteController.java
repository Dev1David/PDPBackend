package com.example.PDPMobileGame.controller;

import com.example.PDPMobileGame.DTO.SpinRouletteRequest;
import com.example.PDPMobileGame.entity.UserEntity;
import com.example.PDPMobileGame.exception.NotEnoughFundsException;
import com.example.PDPMobileGame.repository.UserRepository;
import com.example.PDPMobileGame.service.RouletteService;
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

    RouletteService rouletteService = new RouletteService();

    @PostMapping
    public ResponseEntity<?> spinRouletteWithPercentage (
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody SpinRouletteRequest spinRouletteRequest
    ) {
        return rouletteService.spinRouletteWithPercentage(token, spinRouletteRequest);
    }
}
