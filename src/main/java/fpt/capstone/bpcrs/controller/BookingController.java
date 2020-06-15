package fpt.capstone.bpcrs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BookingPayload;
import fpt.capstone.bpcrs.service.BookingService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/booking")
@Slf4j
public class BookingController {

    @Autowired
    BookingService bookingService;

    @GetMapping("/renting/{id}")
    private ResponseEntity<?> getUserRentingBookingList(@PathVariable("id") int id) {
            List<Booking> bookings = bookingService.getUserRentingBookingList(id);
            if (bookings.isEmpty()) {
                return new ResponseEntity(new ApiResponse<>(false, "Dont have any user rent with id = " + id), HttpStatus.BAD_REQUEST);
            }
            List<BookingPayload.ResponseCreateBooking> responses = ObjectMapperUtils.mapAll(bookings, BookingPayload.ResponseCreateBooking.class);
            return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", responses));


    }

    @GetMapping("/hiring/{id}")
    private ResponseEntity<?> getUserHiringBookingList(@PathVariable("id") int id) {
            List<Booking> bookings = bookingService.getUserHiringBookingList(id);
            if (bookings.isEmpty()) {
                return new ResponseEntity(new ApiResponse<>(false, "Dont have any booking with id " + id), HttpStatus.BAD_REQUEST);
            }
            List<BookingPayload.ResponseCreateBooking> responseList = ObjectMapperUtils.mapAll(bookings, BookingPayload.ResponseCreateBooking.class);
            return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", responseList));

    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getBooking(@PathVariable("id") int id) {
        try {
            Booking booking = bookingService.getBookingInformation(id);
            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking, BookingPayload.ResponseCreateBooking.class);
            return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", response));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PostMapping
    private ResponseEntity<?> createBooking( @Valid @RequestBody BookingPayload.RequestCreateBooking request) {
        try {
            BookingPayload.ResponseCreateBooking response = new BookingPayload.ResponseCreateBooking();
            Booking booking = (Booking) new Booking().buildObject(request, true);
                    bookingService.createBooking(booking).buildObject(response, false);
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking was created", response));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateBookingStatus(@PathVariable("id") int id, @Valid @RequestParam String status) {
        try {
            Booking booking = bookingService.updateBookingStatus(id, status);
            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking, BookingPayload.ResponseCreateBooking.class);
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status was updated", response));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}