package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.BookingTracking;

import java.util.List;

public interface BookingTrackingService {
    List<BookingTracking> getAllBookingTrackingByBooking(int bookingId);
}
