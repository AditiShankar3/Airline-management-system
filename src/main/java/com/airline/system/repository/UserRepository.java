package com.airline.system.repository;

import com.airline.system.enums.UserRole;
import com.airline.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/** Owner: Pranav (CS002) */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsernameAndRole(String username, UserRole role);
    // For duplicate-email check across ALL roles
    Optional<User> findByEmail(String email);
    // For duplicate username+role check at registration
    boolean existsByUsernameAndRole(String username, UserRole role);
    List<User> findByRole(UserRole role);
}