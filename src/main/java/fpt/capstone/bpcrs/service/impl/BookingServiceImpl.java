package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.BookingTracking;
import fpt.capstone.bpcrs.payload.BookingPayload;
import fpt.capstone.bpcrs.repository.BookingRepository;
import fpt.capstone.bpcrs.repository.BookingTrackingRepository;
import fpt.capstone.bpcrs.repository.CarRepository;
import fpt.capstone.bpcrs.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BookingTrackingRepository bookingTrackingRepository;

    @Override
    public Booking createBooking(Booking request) {
        request = bookingRepository.save(request);
        bookingTrackingRepository.save(BookingTracking.builder().booking(request).status(request.getStatus()).build());
        return request;
    }

    @Override
    public Booking updateBookingStatus(Booking booking, BookingEnum status) {
        booking.setStatus(status);
        bookingTrackingRepository.save(BookingTracking.builder().booking(booking).status(status).build());
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingInformation(int id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> getUserHiringBookingList(int id) {
        return bookingRepository.findAllByRenter_Id(id);
    }

    @Override
    public List<Booking> getUserRentingBookingList(int id) {
        return bookingRepository.findAllByRenter_Id(id);
    }

    @Override
    public Booking finishBooking(int id, int money) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            if (money != 0) {
            }
            bookingRepository.save(booking);
        }
        return booking;
    }

    @Override
    public Booking statisticCarDamage(int id, BookingPayload.RequestStatisticCarDamage request) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            if (request.isDamage()) {
//                booking.setFixingPrice(booking.getRentPrice() - request.getFixPrice());
                booking.setLocation(request.getDamageDescription());
                bookingRepository.save(booking);
            }
        }
        return booking;
    }

    @Override
    public Page<Booking> getAllBookingsRequestByCar(int carId, BookingEnum[] status, int page, int size) {
        return bookingRepository.findAllByCarIdAndStatusInOrderByCreatedDateDesc(carId, status,
                new Paging(page, size, Sort.unsorted()));
    }

    @Override
    public Page<Booking> getAllBookingRequestsByOwner(int onwerId, BookingEnum[] status, int page, int size) {
        return bookingRepository.findAllByCar_Owner_IdAndStatusInOrderByCreatedDateDesc(onwerId, status, new Paging(page, size, Sort.unsorted()));
    }

    @Override
    public Page<Booking> getAllBookingRequestsByRenter(int renterId, BookingEnum[] status, int page, int size) {
        return bookingRepository.findAllByRenter_IdAndStatusInOrderByCreatedDateDesc(renterId, status,
                new Paging(page, size, Sort.unsorted()));
    }

    @Override
    public boolean checkStatusBookingBySM(BookingEnum currentStatus, BookingEnum nextStatus) {
        switch (currentStatus) {
            case REQUEST:
                return nextStatus == BookingEnum.PENDING || nextStatus == BookingEnum.DENY || nextStatus == BookingEnum.CANCEL;
            case PENDING:
                return  nextStatus == BookingEnum.CANCEL || nextStatus == BookingEnum.OWNER_ACCEPTED;
            case DENY:
                return nextStatus == BookingEnum.REQUEST;
            case OWNER_ACCEPTED:
                return nextStatus == BookingEnum.CONFIRM;
            case CONFIRM:
                return nextStatus == BookingEnum.CANCEL || nextStatus == BookingEnum.RENTER_SIGNED;
            case RENTER_SIGNED:
                return nextStatus == BookingEnum.PROCESSING;
            case PROCESSING:
                return nextStatus == BookingEnum.DONE;
        }
        return false;
    }


    @Override
    public void updateBookingDuplicateDate(Booking approveBooking, BookingEnum status) {
        List<Booking> bookingList = bookingRepository.findAllByFromDateBetweenOrToDateBetweenAndCarIdAndStatus(approveBooking.getFromDate(), approveBooking.getToDate(), approveBooking.getFromDate(), approveBooking.getToDate(), approveBooking.getCar().getId(), BookingEnum.REQUEST);
        bookingList.stream().forEach(item -> log.info(item.getStatus().toString()));
        bookingList.stream().filter(booking -> booking.getId() != approveBooking.getId()).forEach(booking -> updateBookingStatus(booking, status));
    }

    @Override
    public Double sumAllBookingTotalPriceBetweenDate(LocalDateTime fromDate, LocalDateTime toDate) {
        return bookingRepository.sumTotalPriceBookingByDay(BookingEnum.DONE, fromDate, toDate);
    }

    @Override
    public int getCountRequestByCar(int id) {
        return bookingRepository.countAllByCarIdAndStatus(id,BookingEnum.REQUEST);
    }
}
