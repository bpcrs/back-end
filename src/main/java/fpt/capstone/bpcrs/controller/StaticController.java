package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/static")
@Slf4j
public class StaticController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> getWeeklyRevenue(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {
        try {
        double response = bookingService.sumAllBookingTotalPriceBetweenDate(fromDate, toDate);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
        } catch (IllegalArgumentException ex) {
            ex.getMessage();
        }
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Can not sum weekly revenue" , HttpStatus.BAD_REQUEST));
    }
}
