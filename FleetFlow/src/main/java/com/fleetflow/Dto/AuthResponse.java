package com.fleetflow.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO retourné après connexion réussie.
 * Contient le token JWT à utiliser dans les requêtes suivantes.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private String role;
}