package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BookingPayload;
import fpt.capstone.bpcrs.payload.PagingPayload;
import fpt.capstone.bpcrs.service.AccountService;
import fpt.capstone.bpcrs.service.AgreementService;
import fpt.capstone.bpcrs.service.BookingService;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    private AgreementService agreementService;

    @GetMapping("/renting/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getUserRentingBookingList(@PathVariable("id") int id) {
        List<Booking> bookings = bookingService.getUserRentingBookingList(id);
        if (bookings.isEmpty()) {
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any user rent with id = " + id), HttpStatus.BAD_REQUEST);
        }
        List<BookingPayload.ResponseCreateBooking> responses = ObjectMapperUtils.mapAll(bookings, BookingPayload.ResponseCreateBooking.class);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", responses));
    }

//    @GetMapping("/hiring/{id}")
//    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
//    public ResponseEntity<?> getUserHiringBookingList(@PathVariable("id") int id) {
//        List<Booking> bookings = bookingService.getUserHiringBookingList(id);
//        if (bookings.isEmpty()) {
//            return new ResponseEntity(new ApiResponse<>(false, "Dont have any booking with id " + id), HttpStatus.BAD_REQUEST);
//        }
//        List<BookingPayload.ResponseCreateBooking> responseList = ObjectMapperUtils.mapAll(bookings, BookingPayload.ResponseCreateBooking.class);
//        return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", responseList));
//
//    }

    @GetMapping("/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getBooking(@PathVariable() int id) {
        BookingPayload.ResponseCreateBooking response = new BookingPayload.ResponseCreateBooking();
        Booking booking = bookingService.getBookingInformation(id);
        if (booking != null) {
            booking.buildObject(response, false);
            return ResponseEntity.ok(new ApiResponse<>(true, response));
        }
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Booking with id = " + id + " not found", HttpStatus.BAD_REQUEST));

    }

    @PostMapping
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingPayload.RequestCreateBooking request) {
        Car car = carService.getCarById(request.getCarId());
        Account renter = accountService.getAccountById(request.getRenterId());

        BookingPayload.ResponseCreateBooking response = new BookingPayload.ResponseCreateBooking();

        Booking booking = Booking.builder().car(car).renter(renter)
                .from_date(request.getFromDate()).to_date(request.getToDate())
                .location(request.getLocation()).destination(request.getDestination())
                .status(BookingEnum.REQUEST).totalPrice(request.getTotalPrice()).build();
        bookingService.createBooking(booking).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    @PutMapping("/{id}")
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> updateBookingStatus(@PathVariable("id") int id, @Valid @RequestParam BookingEnum status) {
        try {
            Booking booking = bookingService.getBookingInformation(id);
            if (booking == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Booking with id " + id + " not existed", null));
            }
            if (booking.getCar().getOwner().getId() != accountService.getCurrentUser().getId()) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "User is not allowed", null));
            }
            if (!bookingService.checkStatusBookingBySM(booking.getStatus(), status)) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid status request", null));
            }
            booking = bookingService.updateBookingStatus(booking, status);
            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking, BookingPayload.ResponseCreateBooking.class);
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status was updated", response));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

//    @PutMapping("/return/{id}")
//    @RolesAllowed(RoleEnum.RoleType.USER)
//    public ResponseEntity<?> returnBookingCar(@PathVariable("id") int id) {
//        try {
//            Booking booking = bookingService.updateBookingStatus(id, BookingEnum.RETURN.toString());
//            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking, BookingPayload.ResponseCreateBooking.class);
//            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status was updated", response));
//        } catch (BadRequestException ex) {
//            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
//        }
//    }

    //    @PutMapping("/finish/{id}")
//    @RolesAllowed(RoleEnum.RoleType.USER)
//    private ResponseEntity<?> finishBookingCar(@PathVariable("id") int id, int fixingMoney) {
//        try {
//            Booking booking = bookingService.finishBooking(id, fixingMoney);
//            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking, BookingPayload.ResponseCreateBooking.class);
//            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status was updated", response));
//        } catch (BadRequestException ex) {
//            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
//        }
//    }
//    @PutMapping("/paid/{id}")
//    @RolesAllowed(RoleEnum.RoleType.USER)
//    public ResponseEntity<?> returnMoney(@PathVariable("id") int id) {
//        try {
//            Booking booking = bookingService.updateBookingStatus(id, BookingEnum.PAID.toString());
//            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking, BookingPayload.ResponseCreateBooking.class);
//            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status was updated", response));
//        } catch (BadRequestException ex) {
//            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
//        }
//    }

//    @PutMapping("/checking/{id}")
//    @RolesAllowed(RoleEnum.RoleType.USER)
//    public ResponseEntity<?> checkCar(@PathVariable("id") int id, @Valid @RequestBody BookingPayload.RequestStatisticCarDamage request) {
//        try {
//            Booking booking = bookingService.statisticCarDamage(id, request);
//            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking, BookingPayload.ResponseCreateBooking.class);
//            return ResponseEntity.ok(new ApiResponse<>(true, "Car is checked", response));
//        } catch (BadRequestException ex) {
//            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
//        }
//    }


//    @PutMapping("/finish/{id}")
//    @RolesAllowed(RoleEnum.RoleType.USER)
//    public ResponseEntity<?> finishBookingCar(@PathVariable("id") int id, int fixingMoney) {
//        try {
//            Booking booking = bookingService.finishBooking(id, fixingMoney);
//            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking, BookingPayload.ResponseCreateBooking.class);
//            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status was updated", response));
//        } catch (BadRequestException ex) {
//            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
//        }
//    }

    @GetMapping("/car/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getAllBookingRequestByCar(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable() int id,
            @Valid @RequestParam BookingEnum[] status) {
        Page<Booking> bookings = bookingService.getAllBookingsRequestByCar(id, status, page, size);
        List<BookingPayload.ResponseCreateBooking> responses = ObjectMapperUtils.mapAll(bookings.toList(),
                BookingPayload.ResponseCreateBooking.class);
        PagingPayload pagingPayload =
                PagingPayload.builder().data(responses).count((int) bookings.getTotalElements()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
    }

    @GetMapping("/user/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getAllBookingRequestsByUser(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable() int id,
            @Valid @RequestParam BookingEnum[] status,
            @RequestParam boolean isRenter) {
        Page<Booking> bookings;
        if (isRenter) {
            bookings = bookingService.getAllBookingRequestsByRenter(id, status, page, size);
        } else {
            bookings = bookingService.getAllBookingRequestsByOwner(id, status, page, size);
        }
        if (bookings == null) {
            String role = isRenter ? "renter" : "owner";
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Dont have any bookings with user id = " + id + " with role = " + role, HttpStatus.BAD_REQUEST));
        }
        List<BookingPayload.ResponseCreateBooking> responses = ObjectMapperUtils.mapAll(bookings.toList(),
                BookingPayload.ResponseCreateBooking.class);
        PagingPayload pagingPayload =
                PagingPayload.builder().data(responses).count((int) bookings.getTotalElements()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
    }


}
