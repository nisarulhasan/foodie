package com.food_delivey.food_riding.service;

import com.food_delivey.food_riding.dto.JwtResponse;
import com.food_delivey.food_riding.dto.LoginRequest;
import com.food_delivey.food_riding.dto.RegistrationRequest;
import com.food_delivey.food_riding.model.User;
import com.food_delivey.food_riding.repository.UserRepository;
import com.food_delivey.food_riding.security.JwtUtils;
import com.food_delivey.food_riding.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public void registerUser(RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setEnabled(false);

        userRepository.save(user);
        otpService.generateAndSendOtp(user.getEmail());
    }

    public void activateUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public JwtResponse loginUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);

        return new JwtResponse(
                jwt,
                userDetails.getUsername(),
                userRepository.findByEmail(userDetails.getUsername()).get().getRole()
        );
    }
}