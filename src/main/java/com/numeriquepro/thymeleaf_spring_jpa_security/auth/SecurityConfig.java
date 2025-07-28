package com.numeriquepro.thymeleaf_spring_jpa_security.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/store/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        // Accès uniquement au rôle ADMIN
                        .requestMatchers("/admin-only").hasRole("ADMIN")

                        // ADMIN ET ANIMATEUR
                        .requestMatchers("/admin-and-animateur").access((authentication, context) -> {
                            var authorities = authentication.get().getAuthorities();
                            boolean hasAdmin = authorities.stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                            boolean hasAnimateur = authorities.stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_ANIMATEUR"));
                            return new AuthorizationDecision(hasAdmin && hasAnimateur);
                        })
                        // Accès aux utilisateurs ayant **l’un ou l’autre des rôles** ADMIN OU ANIMATEUR
                        .requestMatchers("/admin-or-animateur").hasAnyRole("ADMIN", "ANIMATEUR")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout ->
                        logout.logoutSuccessUrl("/")
                        .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )// Active la gestion personnalisée des exceptions de sécurité (accès interdit, etc.)
                .exceptionHandling(exception -> exception

                        // Déclare la page à afficher lorsqu'un utilisateur est authentifié
                        // mais qu'il n'a pas les rôles nécessaires pour accéder à une ressource protégée.
                        // Exemple : un utilisateur avec le rôle CLIENT accède à une route réservée à ADMIN.
                        .accessDeniedPage("/access-denied")
                )

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
