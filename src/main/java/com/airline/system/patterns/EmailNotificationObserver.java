package com.airline.system.patterns;

import com.airline.system.model.Booking;
import org.springframework.stereotype.Component;

/**
 * Observer Pattern — Concrete Observer 1
 * Owner: Pranav (CS002)
 *
 * Reacts to booking events by sending email notifications.
 * Decoupled from BookingService — BookingService doesn't know this class exists.
 */
@Component
public class EmailNotificationObserver implements BookingObserver {

    @Override
    public void onBookingConfirmed(Booking booking) {
        // TODO: integrate JavaMail or SendGrid for production
        System.out.println("[EMAIL] Booking confirmation sent for PNR: " + booking.getPnr()
            + " | Passenger: " + booking.getPassengerId());
    }

    @Override
    public void onBookingCancelled(Booking booking) {
        // TODO: integrate JavaMail or SendGrid for production
        System.out.println("[EMAIL] Cancellation notice sent for PNR: " + booking.getPnr()
            + " | Refund initiated for Passenger: " + booking.getPassengerId());
    }
}
