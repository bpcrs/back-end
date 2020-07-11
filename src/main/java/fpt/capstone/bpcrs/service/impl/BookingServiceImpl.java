package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.repository.BookingRepository;
import fpt.capstone.bpcrs.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
//        if (booking.getStatus().equals(BookingEnum.CREATE.name())) {
//            if (status.equals(BookingEnum.CONFIRM.name()) || status.equals(BookingEnum.DENY.name())) {
//                booking.setStatus(status);
//            }
//        } else if (booking.getStatus().equals(BookingEnum.CONFIRM.name())) {
//            if (status.equals(BookingEnum.DONE.name()) || status.equals(BookingEnum.CANCEL.name())) {
//                booking.setStatus(status);
//            }
//        }
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

//    @Override
//    public Booking finishBooking(int id, int money) {
//        Booking booking = bookingRepository.findById(id).orElse(null);
//        if (booking != null) {
//            booking.setStatus(BookingEnum.DONE.toString());
//            if (money != 0) {
//                booking.setFixingPrice(money);
//            }
//            bookingRepository.save(booking);
//        }
//        return booking;
//    }

}
