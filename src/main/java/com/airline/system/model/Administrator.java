package com.airline.system.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** Owner: Pranav (CS002) */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Administrator extends User {

    private String adminId;
    private int accessLevel;

    @Override
    public boolean login() { return isActive() && accessLevel > 0; }

    @Override
    public void logout() { }
}
