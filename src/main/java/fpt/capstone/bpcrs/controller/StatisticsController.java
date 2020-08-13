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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/statistics")
@Slf4j
public class StatisticsController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingTrackingService bookingTrackingService;

    @GetMapping
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> getTransactionsTotalPrice(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {
        try {
        double response = bookingService.sumAllBookingTotalPriceBetweenDate(fromDate, toDate);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
        } catch (Exception ex) {
            return ResponseEntity.ok(new ApiResponse<>(true, 0));
        }
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
