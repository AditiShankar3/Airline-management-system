package com.airline.system.service;

import com.airline.system.enums.UserRole;
import com.airline.system.model.User;
import com.airline.system.patterns.UserFactory;
import com.airline.system.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Owner: Pranav (CS002)
 * Handles user registration, login, role management.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(UserRole role, String username, String email, String password) {
        // Factory pattern: creates correct subtype (Passenger / Staff / Admin)
        User newUser = UserFactory.createUser(role, username, email, password);
        return userRepository.save(newUser);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void setUserActive(String userId, boolean active) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setActive(active);
            userRepository.save(user);
        });
    }
}
