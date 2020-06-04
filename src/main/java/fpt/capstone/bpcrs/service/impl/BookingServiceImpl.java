package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.payload.BookingRequest;
import fpt.capstone.bpcrs.repository.AccountRepository;
import fpt.capstone.bpcrs.repository.BookingRepository;
import fpt.capstone.bpcrs.repository.CarRepository;
import fpt.capstone.bpcrs.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CarRepository carRepository;

    @Override
    public Booking createBooking(BookingRequest request) {
        if (accountRepository.findById(request.getId_lessor()).isPresent()
                && accountRepository.findById(request.getId_renter()).isPresent()
                && carRepository.findById(request.getId_car()).isPresent()) {
            Account renter = accountRepository.findById(request.getId_renter()).get();
            Account lessor = accountRepository.findById(request.getId_lessor()).get();
            Car car = carRepository.findById(request.getId_car()).get();
            return bookingRepository.save(Booking.builder()
                    .lessor(lessor)
                    .renter(renter)
                    .car(car)
                    .description(request.getDescription())
                    .status(BookingEnum.CREATED)
                    .price(request.getPrice())
                    .to_date(request.getTo_date())
                    .from_date(request.getFrom_date())
                    .build());
        } else {
            throw new BadRequestException("Can not created this booking");
        }
    }

    @Override
    public Booking confirmBookingStatus(int id, BookingRequest request) {
        if (bookingRepository.findById(id).isPresent()) {
            Booking booking = bookingRepository.findById(id).get();
            booking.setStatus(BookingEnum.CONFIRM);
            return bookingRepository.save(booking);
        } else {
            throw new BadRequestException("Can not confirm this booking");
        }
    }

    @Override
    public Booking cancelBookingStatus(int id, BookingRequest request) {
        if (bookingRepository.findById(id).isPresent()) {
            Booking booking = bookingRepository.findById(id).get();
            booking.setStatus(BookingEnum.CANCELED);
            return bookingRepository.save(booking);
        } else {
            throw new BadRequestException("Can not cancel this booking");
        }
    }

    @Override
    public Booking denyBookingStatus(int id, BookingRequest request) {
        if (bookingRepository.findById(id).isPresent()) {
            Booking booking = bookingRepository.findById(id).get();
            booking.setStatus(BookingEnum.DENIED);
            return bookingRepository.save(booking);
        } else {
            throw new BadRequestException("Can not deny this booking");
        }
    }

    @Override
    public Booking updateBookingInformation(int id, BookingRequest request) {
        if (bookingRepository.findById(id).isPresent()) {
            Booking booking = bookingRepository.findById(id).get();
            booking.setDescription(request.getDescription());
            booking.setDestination(request.getDestination());
            booking.setFrom_date(request.getFrom_date());
            booking.setTo_date(request.getTo_date());
            booking.setPrice(request.getPrice());
            return bookingRepository.save(booking);
        } else {
            throw new BadRequestException("Can not update this booking");
        }
    }

    @Override
    public Booking getBookingInformation(int id) {
        if (bookingRepository.findById(id).isPresent()) {
            return bookingRepository.findById(id).get();
        } else {
            throw new BadRequestException("Booking not found!");
        }
    }

    @Override
    public List<Booking> getListRentingBooking(int id) {
        if (accountRepository.findById(id).isPresent()) {
            return bookingRepository.getByRenter_Id(id);
        } else {
            throw new BadRequestException("Can not view list history");
        }
    }

    @Override
    public List<Booking> getListHiringBooking(int id) {
        if (accountRepository.findById(id).isPresent()) {
            return bookingRepository.getByLessor_Id(id);
        } else {
            throw new BadRequestException("Can not view list history");
        }
    }

    @Override
    public List<Booking> getListPreviousBooking(int renter, int lessor) {
        if (accountRepository.findById(renter).isPresent() && accountRepository.findById(lessor).isPresent()) {
            return bookingRepository.getByRenter_IdAndLessor_Id(renter, lessor);
        } else {
            throw new BadRequestException("Can not view list history");
        }
    }
}
