package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
public class BookingTrackingPayload {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseBookingTracking {
        @NotNull
        private int id;

        @NotNull
        private LocalDateTime createdDate;

        @NotNull
        private String status;

        @NotNull
        private int bookingId;
    }
}
