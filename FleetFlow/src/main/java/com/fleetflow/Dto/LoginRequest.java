package com.fleetflow.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO pour la connexion.
 * Reçu dans le body de POST /api/auth/login
 */
@Data
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}