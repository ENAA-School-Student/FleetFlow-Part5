package com.fleetflow.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Désactiver CSRF (inutile pour une API REST stateless)
                .csrf(AbstractHttpConfigurer::disable)

                // Règles d'autorisation des URLs
                .authorizeHttpRequests(auth -> auth

                        // Endpoints publics : inscription et connexion
                        .requestMatchers("/api/auth/**").permitAll()

                        // Swagger (optionnel, pour les tests)
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Gestion des utilisateurs : ADMIN uniquement
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // Supprimer un chauffeur : ADMIN uniquement
                        .requestMatchers(HttpMethod.DELETE, "/api/chauffeurs/**").hasRole("ADMIN")

                        // Consulter chauffeurs : ADMIN et MANAGER
                        .requestMatchers(HttpMethod.GET, "/api/chauffeurs/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // Consulter véhicules : ADMIN et MANAGER
                        .requestMatchers(HttpMethod.GET, "/api/vehicules/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // Toute autre requête doit être authentifiée
                        .anyRequest().authenticated()
                )

                // API REST = pas de session HTTP
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Configurer le provider d'authentification
                .authenticationProvider(authenticationProvider())

                // Insérer notre filtre JWT avant le filtre username/password
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Provider qui utilise la base de données pour vérifier les credentials.
     * Il utilise UserDetailsService pour charger l'utilisateur
     * et BCrypt pour vérifier le mot de passe.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * BCrypt est l'algorithme de hachage recommandé pour les mots de passe.
     * Il est "lent" par design pour résister aux attaques brute-force.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager est nécessaire pour le AuthService.login().
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}