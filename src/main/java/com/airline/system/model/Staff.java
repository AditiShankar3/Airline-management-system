package com.airline.system.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** Owner: Pranav (CS002) */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Staff extends User {

    private String staffId;
    private String designation;
    private String department;

    @Override
    public boolean login() { return isActive(); }

    @Override
    public void logout() { }
}
