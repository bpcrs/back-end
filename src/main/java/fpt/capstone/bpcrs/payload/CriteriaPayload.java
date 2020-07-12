package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
    }
}
