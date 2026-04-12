package com.airline.system.controller;

import com.airline.system.enums.UserRole;
import com.airline.system.model.User;
import com.airline.system.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Owner: Pranav (CS002)
 * Handles /api/auth/register and /api/auth/login.
 * TODO (Pranav): Add JWT token generation on login.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest req) {
        User user = userService.registerUser(
            req.getRole(), req.getUsername(), req.getEmail(), req.getPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req) {
        // TODO (Pranav): validate credentials and return JWT token
        User user = userService.findByUsername(req.getUsername());
        return ResponseEntity.ok("Login successful for: " + user.getUsername()
            + " | Role: " + user.getRole());
    }

    @Data
    static class RegisterRequest {
        private String username;
        private String email;
        private String password;
        private UserRole role;
    }

    @Data
    static class LoginRequest {
        private String username;
        private String password;
    }
}
