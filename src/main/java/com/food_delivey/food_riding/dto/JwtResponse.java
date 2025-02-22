package com.food_delivey.food_riding.dto;

import com.food_delivey.food_riding.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String email;
    private Role role;

    public JwtResponse(String token) {
    }
}