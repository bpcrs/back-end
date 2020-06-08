package fpt.capstone.bpcrs.payload;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    }

    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ResponseCreateReview extends RequestCreateReview{
        private int id;
    }


}
