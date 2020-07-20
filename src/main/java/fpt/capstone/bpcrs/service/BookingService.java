package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.payload.BookingPayload;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {

    Booking createBooking(Booking booking);

    Booking updateBookingStatus(int id, String status);

    Booking getBookingInformation(int id);

    List<Booking> getUserHiringBookingList(int id);

    List<Booking> getUserRentingBookingList(int id);

    Booking finishBooking(int id, int money);

    Booking statisticCarDamage(int id, BookingPayload.RequestStatisticCarDamage request);

    Page<Booking> getAllBookingsRequestByOwner(int ownerId, int page, int size);
}
