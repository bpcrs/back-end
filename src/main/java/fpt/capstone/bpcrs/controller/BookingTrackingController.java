package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.BookingTracking;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BookingTrackingPayload;
import fpt.capstone.bpcrs.service.BookingService;
import fpt.capstone.bpcrs.service.BookingTrackingService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tracking")
@Slf4j
public class BookingTrackingController {

    @Autowired
    private BookingTrackingService bookingTrackingService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{id}")
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> getAllBookingTrackingByBooking(@PathVariable() int id) {
        Booking booking = bookingService.getBookingInformation(id);
        if (booking == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Booking with id = " + id + " not found", null));
        }
        List<BookingTracking> list = bookingTrackingService.getAllBookingTrackingByBooking(id);
        List<BookingTrackingPayload.ResponseBookingTracking> response = ObjectMapperUtils.mapAll(list, BookingTrackingPayload.ResponseBookingTracking.class);

        return ResponseEntity.ok(new ApiResponse<>(true, response));

    }

    @PostMapping()
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> getAllBookingWithFromDateToDateAndStatus(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
                                                                      @RequestParam BookingEnum status){
        LocalDateTime fromDateTime = LocalDateTime.ofInstant(fromDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime toDateTime = LocalDateTime.ofInstant(toDate.toInstant(), ZoneId.systemDefault());
        List<BookingTracking> bookingTrackingList = bookingTrackingService.getAllBookingWithFromDateAndToDate(fromDateTime,toDateTime,status);
        List<BookingTrackingPayload.ResponseBookingTracking> response = ObjectMapperUtils.mapAll(bookingTrackingList, BookingTrackingPayload.ResponseBookingTracking.class);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }
}
