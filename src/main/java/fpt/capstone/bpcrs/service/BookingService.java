package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.payload.BookingPayload;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BookingService {

    Booking createBooking(Booking booking);

    Booking updateBookingStatus(Booking  booking, BookingEnum status);

    Booking getBookingInformation(int id);

    List<Booking> getUserHiringBookingList(int id);

    List<Booking> getUserRentingBookingList(int id);

    Booking finishBooking(int id, int money);

    Booking statisticCarDamage(int id, BookingPayload.RequestStatisticCarDamage request);

    //Car
    Page<Booking> getAllBookingsRequestByCar(int carId, BookingEnum[] status, int page, int size);

    //Owner
    Page<Booking> getAllBookingRequestsByOwner(int onwerId,BookingEnum[] status, int page, int size);

    //Renter
    Page<Booking> getAllBookingRequestsByRenter(int renterId, BookingEnum[] status, int page, int size);

    boolean checkStatusBookingBySM (BookingEnum currentStatus, BookingEnum nextStatus);

    void updateCancelBookingDuplicateDate(Booking approveBooking);

    int getCountRequestByCar(int id);
    void updateBookingDuplicateDate(Booking approveBooking, BookingEnum status);

    double sumAllBookingTotalPriceBetweenDate(LocalDateTime fromDate, LocalDateTime toDate);
}
