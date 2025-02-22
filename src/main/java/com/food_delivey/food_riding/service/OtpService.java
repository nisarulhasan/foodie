package com.food_delivey.food_riding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final RedisTemplate<String, String> redisTemplate;
    private final EmailService emailService;

        public void generateAndSendOtp(String email) {
            String otp = String.format("%06d", new Random().nextInt(999999)); // Corrected line
            redisTemplate.opsForValue().set(email, otp, Duration.ofMinutes(5)); // Corrected line
            emailService.sendOtpEmail(email, otp);
        }


    public boolean verifyOtp(String email, String otp) {
        String storedOtp = redisTemplate.opsForValue().get(email);
        return otp.equals(storedOtp);
    }
}