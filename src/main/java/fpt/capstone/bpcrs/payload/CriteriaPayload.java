package fpt.capstone.bpcrs.payload;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CriteriaPayload {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateCriteria  {
        @NotNull
        private String name;

        @NotNull
        private float unit;
    }

    @Data
    @NoArgsConstructor
    public static class ResposneCreateCriteria {
        private int id;

        @NotNull
        private String name;

        @NotNull
        private float unit;
    }
}
