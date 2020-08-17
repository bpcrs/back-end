package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.exception.BpcrsException;
import fpt.capstone.bpcrs.model.Booking;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    Booking createBooking(Booking booking);

    Booking updateBookingStatus(Booking  booking, BookingEnum status) throws BpcrsException;

    Booking getBookingInformation(int id);

    List<Booking> getUserHiringBookingList(int id);

    List<Booking> getUserRentingBookingList(int id);

    //Car
    Page<Booking> getAllBookingsRequestByCar(int carId, BookingEnum[] status, int page, int size);

    //Owner
    Page<Booking> getAllBookingRequestsByOwner(int onwerId,BookingEnum[] status, int page, int size);

    //Renter
    Page<Booking> getAllBookingRequestsByRenter(int renterId, BookingEnum[] status, int page, int size);


    int getCountRequestByCar(int id);
    List<Booking> updateBookingDuplicateDate(Booking approveBooking, BookingEnum status) throws BpcrsException;

    Double sumAllBookingTotalPriceBetweenDate(LocalDateTime fromDate, LocalDateTime toDate);
}
