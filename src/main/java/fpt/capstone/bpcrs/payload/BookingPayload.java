package fpt.capstone.bpcrs.payload;

import fpt.capstone.bpcrs.constant.BookingEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
public class BookingPayload {

    @Data
    @NoArgsConstructor
    public static class RequestCreateBooking {
        @Min(1)
        private int carId;

        @Min(1)
        private int renterId;

        @NotNull
        private String location;

        @NotNull
        private BookingEnum status;

        @NotNull
        private String destination;

        @NotNull
        private Date toDate;

        @NotNull
        private Date fromDate;

        @NotNull
        private double totalPrice;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseCreateBooking  {

        @NotNull
        private int id;

        private CarPayload.ResponseGetCar car;

        private AccountPayload.AccountResponse renter;

        @NotNull
        private String location;

        @NotNull
        private String status;

        @NotNull
        private String destination;

        @NotNull
        private Date toDate;

        @NotNull
        private Date fromDate;

        @NotNull
        private double totalPrice;

        private LocalDateTime createdDate;
    }

    @Data
    @NoArgsConstructor
    public static class RequestStatisticCarDamage {
        private boolean isDamage;

        private String damageDescription;

        private int fixPrice;
    }
}
