package com.airline.system.patterns;

import com.airline.system.model.Booking;

/**
 * Observer Pattern — interface for all booking event listeners.
 * Owner: Pranav (CS002) + 4th Member
 */
public interface BookingObserver {
    void onBookingConfirmed(Booking booking);
    void onBookingCancelled(Booking booking);
}
