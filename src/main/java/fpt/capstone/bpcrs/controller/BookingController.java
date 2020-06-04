package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.component.AuthenticationFacade;
import fpt.capstone.bpcrs.component.UserPrincipal;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BookingRequest;
import fpt.capstone.bpcrs.payload.BookingResponse;
import fpt.capstone.bpcrs.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @GetMapping("/booking/renting")
    private ResponseEntity<?> getUserRentingBookingList() {
        try {
            Authentication auth = authenticationFacade.getAuthentication();
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            List<Booking> booking = bookingService.getListRentingBooking(userPrincipal.getId());
            List<BookingResponse> responses = booking.stream()
                    .map(BookingResponse::setResponse).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", responses));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @GetMapping("/booking/hiring")
    private ResponseEntity<?> getUserHiringBookingList() {
        try {
            Authentication auth = authenticationFacade.getAuthentication();
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            List<Booking> booking = bookingService.getListHiringBooking(userPrincipal.getId());
            List<BookingResponse> responses = booking.stream()
                    .map(BookingResponse::setResponse).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", responses));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @GetMapping("/booking/renting/{id}")
    private ResponseEntity<?> getBookingRentingList(@PathVariable("id") int id) {
        try {
            List<Booking> booking = bookingService.getListRentingBooking(id);
            List<BookingResponse> responses = booking.stream()
                    .map(BookingResponse::setResponse).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", responses));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @GetMapping("/booking/hiring/{id}")
    private ResponseEntity<?> getBookingHiringList(@PathVariable("id") int id) {
        try {
            List<Booking> booking = bookingService.getListHiringBooking(id);
            List<BookingResponse> responses = booking.stream()
                    .map(BookingResponse::setResponse).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", responses));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }


    @PostMapping("/booking")
    private ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequest request) {
        Authentication auth = authenticationFacade.getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        request.setId_lessor(userPrincipal.getId());
        try {
            Booking booking = bookingService.createBooking(request);
            BookingResponse response = BookingResponse.setResponse(booking);
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking was created", response));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PutMapping("/booking/{id}")
    private ResponseEntity<?> updateBooking(@PathVariable("id") int id, @Valid @RequestBody BookingRequest request) {
        try {
            Booking booking = bookingService.updateBookingInformation(id, request);
            BookingResponse response = BookingResponse.setResponse(booking);
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking was updated", response));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}
