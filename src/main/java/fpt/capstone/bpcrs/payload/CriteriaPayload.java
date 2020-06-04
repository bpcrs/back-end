package fpt.capstone.bpcrs.payload;

import fpt.capstone.bpcrs.model.Criteria;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CriteriaPayload {
    public interface Request_CreateCriteria_Validate {

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    public static class RequestCreateCriteria extends Criteria {

    }
}
