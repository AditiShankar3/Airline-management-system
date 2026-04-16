package com.airline.system.patterns;

import com.airline.system.model.Flight;

/**
 * OCP Implementation — Domestic flight rules.
 *
 * Domestic flights operate within the same country; no price adjustment is
 * needed beyond the base price set by the operator.
 *
 * Owner: Alekhya (CS053)
 */
public class DomesticFlight implements FlightType {

    @Override
    public void applyRules(Flight flight) {
        // No price adjustment for domestic routes.
        // Additional domestic-specific rules can be added here without
        // touching FlightFactory or any other class (OCP).
    }
}
