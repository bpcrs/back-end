package fpt.capstone.bpcrs.payload;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.model.Agreement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@NoArgsConstructor
public class AgreementPayload {
    public interface Request_CreateAgreement_Validate {}

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateAgreement extends Agreement {
//        @Min(1)
//        @JsonView(Request_CreateAgreement_Validate.class)
//        private int booking_id;

        @Min(1)
        @JsonView(Request_CreateAgreement_Validate.class)
        private int criteria_id;
    }
}
