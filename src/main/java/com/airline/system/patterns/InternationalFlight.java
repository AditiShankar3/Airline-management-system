package com.airline.system.patterns;

import com.airline.system.model.Flight;

/**
 * OCP Implementation — International flight rules.
 *
 * International routes incur additional operational costs (customs, longer
 * ground handling, fuel surcharges). We model this as a 20 % mark-up on the
 * base price that was supplied by the operator.
 *
 * Owner: Alekhya (CS053)
 */
public class InternationalFlight implements FlightType {

    private static final double INTERNATIONAL_SURCHARGE = 0.20;

    @Override
    public void applyRules(Flight flight) {
        double adjustedPrice = flight.getBasePrice() * (1 + INTERNATIONAL_SURCHARGE);
        flight.setBasePrice(Math.round(adjustedPrice * 100.0) / 100.0); // round to 2 dp
    }
}
