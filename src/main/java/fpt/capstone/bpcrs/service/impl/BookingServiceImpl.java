package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.payload.BookingPayload;
import fpt.capstone.bpcrs.repository.BookingRepository;
import fpt.capstone.bpcrs.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking createBooking(Booking request) {
        return bookingRepository.save(request);
    }

    @Override
    public Booking updateBookingStatus(int id, String status) {
        Booking booking = bookingRepository.getOne(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingInformation(int id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> getUserHiringBookingList(int id) {
        return bookingRepository.findAllByRenter_Id(id);
    }

    @Override
    public List<Booking> getUserRentingBookingList(int id) {
        return bookingRepository.findAllByRenter_Id(id);
    }

    @Override
    public Booking finishBooking(int id, int money) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setStatus(BookingEnum.DONE.toString());
            if (money != 0) {
//                booking.setFixingPrice(money);
            }
            bookingRepository.save(booking);
        }
        return booking;
    }

    @Override
    public Booking statisticCarDamage(int id, BookingPayload.RequestStatisticCarDamage request) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            if (request.isDamage()) {
//                booking.setFixingPrice(booking.getRentPrice() - request.getFixPrice());
                booking.setLocation(request.getDamageDescription());
                bookingRepository.save(booking);
            }
        }
        return booking;
    }

    @Override
    public Page<Booking> getAllBookingsRequestByOwner(int ownerId, int page, int size) {
        Page<Booking> bookings = bookingRepository.findAllByLessor_IdAndStatus(ownerId, BookingEnum.REQUEST.toString(),
                new Paging(page, size, Sort.unsorted()));
//        System.out.println("Booking " + bookings.getSize());
        return bookings;
    }

}
