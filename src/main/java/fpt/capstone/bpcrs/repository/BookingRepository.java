package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> getByRenter_Id(Integer id);

    List<Booking> getByLessor_Id(Integer id);

    List<Booking> getByCar_Id(Integer id);

    List<Booking> getByRenter_IdAndLessor_Id(Integer renter, Integer lessor);
}
