package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
public class BookingPayload {
    public interface Request_CreateBooking_Validate {
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateBooking {

        @Min(1)
        private int carId;

        @Min(1)
        private int lessorId;

        @Min(1)
        private int renterId;

        @NotNull
        private String description;

        @NotNull
        private String status;

        @NotNull
        private String destination;

        @NotNull
        private Date to_date;

        @NotNull
        private Date from_date;

        @NotNull
        private double price;

    }

    @Data
    @NoArgsConstructor
    public static class ResponseCreateBooking {

        private int id;

        @Min(1)
        private int carId;

        @Min(1)
        private int lessorId;

        @Min(1)
        private int renterId;

        @NotNull
        private String description;

        @NotNull
        private String status;

        @NotNull
        private String destination;

        @NotNull
        private Date to_date;

        @NotNull
        private Date from_date;

        @NotNull
        private double price;

    }

    @Data
    @NoArgsConstructor
    public static class RequestStatisticCarDamage {
        private boolean isDamage;

        private String damageDescription;

        private int fixPrice;
    }
}
