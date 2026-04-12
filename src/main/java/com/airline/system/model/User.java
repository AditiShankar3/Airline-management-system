package com.airline.system.model;

import com.airline.system.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Abstract base entity — Owner: Pranav (CS002)
 * LSP: all subtypes (Passenger, Staff, Administrator) are fully substitutable.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    private String username;
    private String password;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime createdDate = LocalDateTime.now();
    private boolean isActive = true;

    public abstract boolean login();   // LSP: every subtype must implement meaningfully
    public abstract void logout();
}
