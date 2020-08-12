package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.model.BookingTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BookingTrackingRepository extends JpaRepository<BookingTracking,Integer> {
    List<BookingTracking> findAllByBooking_Id(int bookingId);
    List<BookingTracking> findAllByCreatedDateBetweenAndStatus(LocalDateTime fromDate, LocalDateTime toDate, BookingEnum status);
}
