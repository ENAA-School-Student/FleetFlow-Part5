package com.fleetflow.Controller;

import com.fleetflow.Dto.AuthResponse;
import com.fleetflow.Dto.LoginRequest;
import com.fleetflow.Dto.RegisterRequest;
import com.fleetflow.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller d'authentification - endpoints publics (pas besoin de JWT).
 *
 * POST /api/auth/register → créer un compte
 * POST /api/auth/login    → se connecter et obtenir un JWT
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
