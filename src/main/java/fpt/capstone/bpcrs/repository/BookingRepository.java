package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByRenter_Id(Integer id);

    List<Booking> findAllByLessor_Id(Integer id);
}
