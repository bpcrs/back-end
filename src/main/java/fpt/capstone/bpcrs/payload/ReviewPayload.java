package fpt.capstone.bpcrs.payload;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@NoArgsConstructor
public class ReviewPayload {
    public interface Request_CreateReview_Validate {

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateReview extends Review {
        @Min(1)
        @JsonView(Request_CreateReview_Validate.class)
        private int carId;
    }
}
