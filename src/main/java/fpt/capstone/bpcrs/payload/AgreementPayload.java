package fpt.capstone.bpcrs.payload;

import com.fasterxml.jackson.annotation.*;
import fpt.capstone.bpcrs.model.Agreement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
public class AgreementPayload {
    public interface Request_CreateAgreement_Validate {}

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

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    public static class RequestUpdateAgreement extends Agreement {

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
