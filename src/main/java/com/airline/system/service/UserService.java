package com.airline.system.service;

import com.airline.system.enums.UserRole;
import com.airline.system.model.User;
import com.airline.system.patterns.UserFactory;
import com.airline.system.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/** Owner: Pranav (CS002) */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Duplicate checks:
     * 1. Same username + same role → "already registered as PASSENGER/STAFF/ADMIN"
     * 2. Same email (any role)     → "email already in use"
     */
    public User registerUser(UserRole role, String username, String email,
                              String password, String phone) {
        if (userRepository.existsByUsernameAndRole(username, role)) {
            throw new RuntimeException(
                "Username '" + username + "' is already registered as " + role +
                ". Please log in instead.");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException(
                "Email '" + email + "' is already in use by another account.");
        }
        User newUser = UserFactory.createUser(role, username, email, password, phone);
        return userRepository.save(newUser);
    }

    public User findByUsernameAndRole(String username, UserRole role) {
        return userRepository.findByUsernameAndRole(username, role)
            .orElseThrow(() -> new RuntimeException(
                "No " + role + " account found for username: " + username));
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public void setUserActive(String userId, boolean active) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setActive(active);
            userRepository.save(user);
        });
    }
}