package com.airline.system.controller;

import com.airline.system.enums.UserRole;
import com.airline.system.model.User;
import com.airline.system.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    // ✅ Returns JSON object, NOT a plain string
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        User user = userService.findByUsername(req.getUsername());
        return ResponseEntity.ok(new LoginResponse(
            user.getUserId(),
            user.getUsername(),
            user.getRole().toString(),
            user.getEmail()
        ));
    }

    // ✅ New endpoint — fixes the 404 error
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ New endpoint — for admin activate/deactivate
    @PutMapping("/users/{id}/status")
    public ResponseEntity<String> setStatus(@PathVariable String id,
                                             @RequestParam boolean active) {
        userService.setUserActive(id, active);
        return ResponseEntity.ok("Status updated");
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

    @Data
    static class LoginResponse {
        private String userId;
        private String username;
        private String role;
        private String email;

        public LoginResponse(String userId, String username, String role, String email) {
            this.userId = userId;
            this.username = username;
            this.role = role;
            this.email = email;
        }
    }
}