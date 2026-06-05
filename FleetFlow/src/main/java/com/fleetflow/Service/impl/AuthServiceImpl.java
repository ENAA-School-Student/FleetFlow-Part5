package com.fleetflow.Service.impl;

import com.fleetflow.Dto.AuthResponse;
import com.fleetflow.Dto.LoginRequest;
import com.fleetflow.Dto.RegisterRequest;
import com.fleetflow.Entity.User;
import com.fleetflow.Repository.UserRepository;
import com.fleetflow.Service.AuthService;
import com.fleetflow.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implémentation du service d'authentification.
 *
 * register() : crée un nouvel utilisateur avec le mot de passe hashé
 * login()     : vérifie les credentials et retourne un JWT
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    /**
     * Inscription d'un nouvel utilisateur.
     * Le mot de passe est hashé avec BCrypt avant d'être sauvegardé.
     */
    @Override
    public AuthResponse register(RegisterRequest request) {
        // Vérifier que le username et l'email ne sont pas déjà pris
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Ce nom d'utilisateur est déjà pris");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        // Construire l'entité User
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // ← BCrypt
                .role(request.getRole())
                .build();

        userRepository.save(user);

        // Générer le token JWT
        String token = jwtUtils.generateToken(user);

        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    /**
     * Connexion d'un utilisateur existant.
     *
     * authenticationManager.authenticate() fait tout le travail :
     *  - charge l'utilisateur via UserDetailsService
     *  - vérifie le mot de passe avec BCrypt
     *  - lève une exception si les credentials sont invalides
     */
    @Override
    public AuthResponse login(LoginRequest request) {
        // Cette ligne vérifie username + password, lève une exception si incorrect
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Si on arrive ici, l'authentification a réussi
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String token = jwtUtils.generateToken(user);

        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }
}
