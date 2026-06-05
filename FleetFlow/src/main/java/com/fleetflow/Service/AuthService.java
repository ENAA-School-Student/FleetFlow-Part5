package com.fleetflow.Service;

import com.fleetflow.Dto.AuthResponse;
import com.fleetflow.Dto.LoginRequest;
import com.fleetflow.Dto.RegisterRequest;

/**
 * Interface du service d'authentification.
 * Définit le contrat : ce que le service DOIT faire.
 */
public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
