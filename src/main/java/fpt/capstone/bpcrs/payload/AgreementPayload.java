package fpt.capstone.bpcrs.payload;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AgreementPayload {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateAgreement {
        @Min(1)
        @NotNull
        private int bookingId;

        @Min(1)
        @NotNull
        private int criteriaId;

        @NotNull
        private String value;

        @NotNull
        private String status;


        private boolean isApproved;
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

        @NotNull
        private String status;


        private boolean isApproved;
    }


}
