package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.payload.BookingRequest;

import java.util.List;

public interface BookingService {

    Booking createBooking(BookingRequest request);

    Booking confirmBookingStatus(int id, BookingRequest request);

    Booking cancelBookingStatus(int id, BookingRequest request);

    Booking denyBookingStatus(int id, BookingRequest request);

    Booking updateBookingInformation(int id, BookingRequest request);

    Booking getBookingInformation(int id);

    List<Booking> getListRentingBooking(int id);

    List<Booking> getListHiringBooking(int id);

    List<Booking> getListPreviousBooking(int renter, int lessor);
}
