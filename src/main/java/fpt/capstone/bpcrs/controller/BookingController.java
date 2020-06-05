package fpt.capstone.bpcrs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BookingPayload;
import fpt.capstone.bpcrs.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        try {
            List<Booking> booking = bookingService.getUserRentingBookingList(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", booking));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @GetMapping("/hiring/{id}")
    private ResponseEntity<?> getUserHiringBookingList(@PathVariable("id") int id) {
        try {
            List<Booking> booking = bookingService.getUserHiringBookingList(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", booking));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getBooking(@PathVariable("id") int id) {
        try {
            Booking booking = bookingService.getBookingInformation(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", booking));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PostMapping
    private ResponseEntity<?> createBooking(@JsonView(BookingPayload.Request_CreateBooking_Validate.class)
                                            @Valid @RequestBody BookingPayload.RequestCreateBooking request) {
        try {

            Booking booking = bookingService.createBooking(request.buildBooking());
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking was created", booking));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateBookingStatus(@PathVariable("id") int id, @Valid @RequestParam String status) {
        try {
            Booking booking = bookingService.updateBookingStatus(id, status);
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status was updated", booking));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}
