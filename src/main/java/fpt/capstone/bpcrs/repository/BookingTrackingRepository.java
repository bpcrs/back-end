package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.BookingTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingTrackingRepository extends JpaRepository<BookingTracking,Integer> {

}
