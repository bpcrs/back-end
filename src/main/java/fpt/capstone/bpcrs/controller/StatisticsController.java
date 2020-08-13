package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.BookingTracking;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BookingTrackingPayload;
import fpt.capstone.bpcrs.payload.StatisticsPayload;
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
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statistics")
@Slf4j
@RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
public class StatisticsController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingTrackingService bookingTrackingService;

    @GetMapping
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
    public ResponseEntity<?> getAllBookingWithFromDateToDateAndStatus(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
                                                                      @RequestParam BookingEnum status){
        LocalDateTime fromDateTime = LocalDateTime.ofInstant(fromDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime toDateTime = LocalDateTime.ofInstant(toDate.toInstant(), ZoneId.systemDefault());
        Integer bookingTrackingListCount = bookingTrackingService.countBookingWithFromDateAndToDate(fromDateTime,toDateTime,status);
        StatisticsPayload.CountBookingResponse response = new StatisticsPayload.CountBookingResponse();
        response.setCount(bookingTrackingListCount);
        LocalDateTime now = LocalDateTime.now();
        List<BookingTracking> bookingTrackingList = bookingTrackingService.getAllBookingWithFromDateAndToDate(now.minusDays(7),now,status);
        Map<LocalDate, Long> mapBooking = bookingTrackingList.stream().collect(Collectors.groupingBy((item) -> item.getCreatedDate().toLocalDate(),Collectors.counting()));
        response.setBookingByDate(mapBooking);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }
}
