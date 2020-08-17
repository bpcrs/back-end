package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
public class CriteriaPayload {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateCriteria  {
        @NotNull
        private String name;

        @NotNull
        private String unit;
    }

    @Data
    @NoArgsConstructor
    public static class ResposneCreateCriteria {
        private int id;

        @NotNull
        private String name;

        @NotNull
        private String unit;

        private boolean isRenter;
    }

    @Data
    @NoArgsConstructor
    public static class PreReturnResponse {
        private int mileageLimit;
        private double extra;
        private double deposit;
        private double totalPrice;
        private double estimatePrice;
        private double insurance;
        private List<AgreementPayload.ResponsePreReturn> agreements;
    }
}
