package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByRenter_Id(Integer id);
    List<Booking> findAllByCar_IdAndStatusIn(int carId, BookingEnum[] status);
    Page<Booking> findAllByRenter_IdAndStatusInOrderByCreatedDateDesc(int ownerId, BookingEnum[] status, Pageable paging);
    Page<Booking> findAllByCar_IdAndStatusInOrderByCreatedDateDesc(int carId, BookingEnum[] status , Pageable paging);
    Page<Booking> findAllByCar_Owner_IdAndStatusInOrderByCreatedDateDesc(int ownerId, BookingEnum[] status , Pageable paging);

    List<Booking> findAllByFromDateBetweenOrToDateBetween(Date fromDate, Date toDate, Date fromDateBetween, Date toDateBetween);
    @Query("select sum (b.rentalPrice) from Booking b where b.status = :status and b.createdDate between :from and :to")
    double sumTotalPriceBookingByDay(BookingEnum status, LocalDateTime from, LocalDateTime to);
    int countAllByCarIdAndStatus(int id, BookingEnum status);

    List<Booking> findAllByStatusIn(BookingEnum[] status);
}
