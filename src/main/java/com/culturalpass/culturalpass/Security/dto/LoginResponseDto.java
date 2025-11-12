package com.culturalpass.culturalpass.Security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String token;
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
}
