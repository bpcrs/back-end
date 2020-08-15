package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
public class AgreementPayload {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateAgreement {

        @Min(1)
        @NotNull
        private int bookingId;

        @NotNull
        @Min(1)
        private int criteriaId;

        @NotNull
        private String value;

    }



    @Data
    @NoArgsConstructor
    public static class ResponseCreateAgreement{
        private int id;

        @Min(1)
        @NotNull
        private int bookingId;

        @Min(1)
        @NotNull
        private int criteriaId;

        @NotNull
        private String value;

        private boolean isApproved;
    }

    @Data
    @NoArgsConstructor
    public static class ResponsePreReturn{
        private CriteriaPayload.ResposneCreateCriteria criteria;

        private String value;
    }


}
