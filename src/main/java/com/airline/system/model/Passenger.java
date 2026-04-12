package com.airline.system.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/** Owner: Pranav (CS002) */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Passenger extends User {

    private String passportNumber;
    private LocalDate dateOfBirth;
    private String address;

    @Override
    public boolean login() { return isActive(); }

    @Override
    public void logout() { /* clear session */ }
}
