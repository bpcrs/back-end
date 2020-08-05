package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.model.Booking;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByRenter_Id(Integer id);

    Page<Booking> findAllByRenter_IdAndStatusIn(int ownerId, BookingEnum[] status, Pageable paging);

    Page<Booking> findAllByCar_IdAndStatusIn(int carId, BookingEnum[] status , Pageable paging);

    Page<Booking> findAllByCar_Owner_IdAndStatusIn(int ownerId, BookingEnum[] status , Pageable paging);

//    List<Booking> findAllByFrom_date(@NotNull Date fromDate);

}
