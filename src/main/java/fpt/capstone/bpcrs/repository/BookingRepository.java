package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Booking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByRenter_Id(Integer id);

    List<Booking> findAllByLessor_Id(Integer id);
}
