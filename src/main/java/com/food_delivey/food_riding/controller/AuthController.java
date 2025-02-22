package com.food_delivey.food_riding.controller;

import com.food_delivey.food_riding.dto.JwtResponse;
import com.food_delivey.food_riding.dto.LoginRequest;
import com.food_delivey.food_riding.dto.OtpVerificationRequest;
import com.food_delivey.food_riding.dto.RegistrationRequest;
import com.food_delivey.food_riding.service.AuthService;
import com.food_delivey.food_riding.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private  AuthService authService;
    @Autowired
    private  OtpService otpService;



    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("OTP sent to email");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpVerificationRequest otpVerificationRequest) {
        if (otpService.verifyOtp(otpVerificationRequest.getEmail(), otpVerificationRequest.getOtp())) {
            authService.activateUser(otpVerificationRequest.getEmail());
            return ResponseEntity.ok("Account activated");
        }
        return ResponseEntity.badRequest().body("Invalid OTP");
    }

    /*@PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        JwtResponse response = authService.loginUser(request);
        return ResponseEntity.ok(response);
    }

     */

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        String token = String.valueOf(authService.loginUser(request));
        return ResponseEntity.ok(new JwtResponse(token));
    }
}