package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByRenter_Id(Integer id);

    Page<Booking> findAllByRenter_Id(int ownerId, Pageable paging);

    Page<Booking> findAllByCar_Id(int carId, Pageable paging);

//    Page<Booking> findAllByRenter_Id(int ownerId, Pageable paging);

}
