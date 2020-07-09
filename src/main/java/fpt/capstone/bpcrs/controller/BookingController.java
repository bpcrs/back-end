package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BookingPayload;
import fpt.capstone.bpcrs.service.AccountService;
import fpt.capstone.bpcrs.service.BookingService;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/booking")
@Slf4j
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CarService carService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/renting/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    private ResponseEntity<?> getUserRentingBookingList(@PathVariable("id") int id) {
        List<Booking> bookings = bookingService.getUserRentingBookingList(id);
        if (bookings.isEmpty()) {
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any user rent with id = " + id), HttpStatus.BAD_REQUEST);
        }
        List<BookingPayload.ResponseCreateBooking> responses = ObjectMapperUtils.mapAll(bookings, BookingPayload.ResponseCreateBooking.class);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", responses));


    }

    @GetMapping("/hiring/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    private ResponseEntity<?> getUserHiringBookingList(@PathVariable("id") int id) {
        List<Booking> bookings = bookingService.getUserHiringBookingList(id);
        if (bookings.isEmpty()) {
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any booking with id " + id), HttpStatus.BAD_REQUEST);
        }
        List<BookingPayload.ResponseCreateBooking> responseList = ObjectMapperUtils.mapAll(bookings, BookingPayload.ResponseCreateBooking.class);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", responseList));

    }

    @GetMapping("/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getBooking(@PathVariable int id) {
        try {
            Booking booking = bookingService.getBookingInformation(id);
            System.out.println("Booking info" + booking);
            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking, BookingPayload.ResponseCreateBooking.class);
            return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", response));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PostMapping
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingPayload.RequestCreateBooking request) {
        Car car = carService.getCarById(request.getCarId());
        Account lessor = accountService.getAccountById(request.getLessorId());
//        Account renter = accountService.getAccountById(request.getRenterId());
        System.out.println("Car id " + request.getCarId());
        System.out.println(car.toString());
        BookingPayload.ResponseCreateBooking response = new BookingPayload.ResponseCreateBooking();
        Booking booking = (Booking) new Booking().buildObject(request, true);
        booking.setCar(car);
        booking.setLessor(car.getOwner());
        booking.setRenter(accountService.getCurrentUser());
        booking.setStatus(BookingEnum.REQUEST.toString());
//        booking.setRenter(accountService.getCurrentUser());
        System.out.println(booking.toString());
//            booking.setStatus(BookingEnum.REQUEST.toString());
        bookingService.createBooking(booking).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    @PutMapping("/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    private ResponseEntity<?> updateBookingStatus(@PathVariable("id") int id, @Valid @RequestParam String status) {
        try {
            Booking booking = bookingService.updateBookingStatus(id, status);
            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking, BookingPayload.ResponseCreateBooking.class);
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status was updated", response));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PutMapping("/return/{id}")
    @RolesAllowed(RoleEnum.RoleType.USER)
    private ResponseEntity<?> returnBookingCar(@PathVariable("id") int id) {
        try {
            Booking booking = bookingService.updateBookingStatus(id, BookingEnum.RETURN.toString());
            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking, BookingPayload.ResponseCreateBooking.class);
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status was updated", response));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PutMapping("/finish/{id}")
    @RolesAllowed(RoleEnum.RoleType.USER)
    private ResponseEntity<?> finishBookingCar(@PathVariable("id") int id, int fixingMoney) {
        try {
            Booking booking = bookingService.finishBooking(id, fixingMoney);
            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking, BookingPayload.ResponseCreateBooking.class);
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status was updated", response));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}
