package com.airline.system.patterns;

import com.airline.system.enums.UserRole;
import com.airline.system.model.Administrator;
import com.airline.system.model.Passenger;
import com.airline.system.model.Staff;
import com.airline.system.model.User;

/**
 * Factory Pattern — Creational
 * Owner: Alekhya (CS053)
 *
 * Problem solved: Without Factory, every registration endpoint would have
 * duplicated if-else blocks deciding which User subtype to instantiate.
 * Factory centralises that decision in one place.
 */
public class UserFactory {

    public static User createUser(UserRole role, String username,
                                   String email, String password) {
        return switch (role) {
            case PASSENGER -> {
                Passenger p = new Passenger();
                p.setUsername(username);
                p.setEmail(email);
                p.setPassword(password);
                p.setRole(UserRole.PASSENGER);
                p.setActive(true);
                yield p;
            }
            case STAFF -> {
                Staff s = new Staff();
                s.setUsername(username);
                s.setEmail(email);
                s.setPassword(password);
                s.setRole(UserRole.STAFF);
                s.setActive(true);
                yield s;
            }
            case ADMIN -> {
                Administrator a = new Administrator();
                a.setUsername(username);
                a.setEmail(email);
                a.setPassword(password);
                a.setRole(UserRole.ADMIN);
                a.setActive(true);
                yield a;
            }
        };
    }
}
