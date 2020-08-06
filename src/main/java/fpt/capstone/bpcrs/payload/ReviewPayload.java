package fpt.capstone.bpcrs.payload;

import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;


@NoArgsConstructor
public class ReviewPayload {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateReview{
        @Min(1)
        private int carId;

        @NotNull
        private int rating;

        @NotNull
        private String comment;

        @NotNull
        private int bookingId;

    }

    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ResponseCreateReview extends RequestCreateReview{
        private LocalDateTime createdDate;
        private int id;
        private AccountPayload.AccountResponse renter;

    }

}
