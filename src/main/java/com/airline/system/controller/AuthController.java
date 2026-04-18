package com.airline.system.controller;

import com.airline.system.enums.UserRole;
import com.airline.system.model.User;
import com.airline.system.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) { this.userService = userService; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            User user = userService.registerUser(
                req.getRole(), req.getUsername(), req.getEmail(),
                req.getPassword(), req.getPhone());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            // Return 409 Conflict with the error message so frontend can show alert
            return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            User user = userService.findByUsernameAndRole(req.getUsername(), req.getRole());
            return ResponseEntity.ok(new LoginResponse(
                user.getUserId(), user.getUsername(),
                user.getRole().toString(), user.getEmail()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<String> setStatus(@PathVariable String id,
                                             @RequestParam boolean active) {
        userService.setUserActive(id, active);
        return ResponseEntity.ok("Status updated");
    }

    @Data static class RegisterRequest {
        private String username, email, password, phone;
        private UserRole role;
    }
    @Data static class LoginRequest {
        private String username, password;
        private UserRole role;
    }
    @Data static class LoginResponse {
        private String userId, username, role, email;
        public LoginResponse(String userId, String username, String role, String email) {
            this.userId = userId; this.username = username;
            this.role = role; this.email = email;
        }
    }
}