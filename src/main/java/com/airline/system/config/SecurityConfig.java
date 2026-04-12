package com.airline.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * STARTER SECURITY CONFIG — permits all requests so teammates can test APIs immediately.
 * TODO (Pranav - CS002): Replace this entire class with JWT-based role security.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // TODO: Pranav — replace with JWT + role-based rules
            );
        return http.build();
    }
}
