package com.airline.system.patterns;

import com.airline.system.model.Flight;

/**
 * Open/Closed Principle — Strategy interface for flight type rules.
 *
 * The system is OPEN for extension (add new flight types by implementing this
 * interface) but CLOSED for modification (FlightFactory never needs an if-else
 * chain; it simply calls applyRules polymorphically).
 *
 * Owner: Alekhya (CS053)
 */
public interface FlightType {

    /**
     * Apply type-specific business rules to the given flight.
     * Implementations may alter price, add surcharges, validate constraints, etc.
     *
     * @param flight the freshly constructed Flight entity to mutate
     */
    void applyRules(Flight flight);
}
