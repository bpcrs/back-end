package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
public class ModelPayload {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateModel {
        @NotNull
        private String name;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseCreateModel extends ModelPayload.RequestCreateModel {
        private int id;
    }
}
