package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class StatisticsPayload {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CountBookingResponse{
        private int count;
        private Map<LocalDate, Long> bookingByDate;
    }
}
