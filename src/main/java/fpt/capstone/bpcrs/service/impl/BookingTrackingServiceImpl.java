package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.model.BookingTracking;
import fpt.capstone.bpcrs.repository.BookingTrackingRepository;
import fpt.capstone.bpcrs.service.BookingTrackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookingTrackingServiceImpl implements BookingTrackingService {

    @Autowired
    private BookingTrackingRepository bookingTrackingRepository;


    @Override
    public List<BookingTracking> getAllBookingTrackingByBooking(int bookingId) {
        return bookingTrackingRepository.findAllByBooking_Id(bookingId);
    }
}
