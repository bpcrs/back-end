package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.model.Booking;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByRenter_Id(Integer id);

    Page<Booking> findAllByRenter_IdAndStatusInOrderByCreatedDateDesc(int ownerId, BookingEnum[] status, Pageable paging);

    Page<Booking> findAllByCar_IdAndStatusInOrderByCreatedDateDesc(int carId, BookingEnum[] status , Pageable paging);

    Page<Booking> findAllByCar_Owner_IdAndStatusInOrderByCreatedDateDesc(int ownerId, BookingEnum[] status , Pageable paging);

    List<Booking> findAllByFromDateLessThanEqualAndCarId(Date date, int carId);
}
