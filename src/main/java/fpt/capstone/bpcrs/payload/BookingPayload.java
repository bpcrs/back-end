package fpt.capstone.bpcrs.payload;

import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    }
}
