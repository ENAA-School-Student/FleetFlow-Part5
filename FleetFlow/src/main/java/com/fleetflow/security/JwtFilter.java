package com.fleetflow.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Lire l'en-tête Authorization
        final String authHeader = request.getHeader("Authorization");

        // Si l'en-tête est absent ou ne commence pas par "Bearer ", on passe au filtre suivant
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraire le token (enlever "Bearer ")
        final String jwt = authHeader.substring(7);

        // 3. Extraire le username du token
        final String username = jwtUtils.extractUsername(jwt);

        // On n'authentifie que si le username est présent et que l'utilisateur
        // n'est pas déjà authentifié dans ce contexte
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 4. Charger l'utilisateur depuis la base de données
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 5. Valider le token
            if (jwtUtils.isTokenValid(jwt, userDetails)) {

                // 6. Créer l'objet d'authentification Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities() // les rôles
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Placer dans le SecurityContext → l'utilisateur est authentifié
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}