package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.model.BookingTracking;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BookingTrackingService {
    List<BookingTracking> getAllBookingTrackingByBooking(int bookingId);
    List<BookingTracking> getAllBookingWithFromDateAndToDate(LocalDateTime fromDate, LocalDateTime toDate, BookingEnum status);
    Integer countBookingWithFromDateAndToDate(LocalDateTime fromDate, LocalDateTime toDate, BookingEnum status);
}
