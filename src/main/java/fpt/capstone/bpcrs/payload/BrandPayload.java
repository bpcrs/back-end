package fpt.capstone.bpcrs.payload;

import fpt.capstone.bpcrs.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
public class BrandPayload {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateBrand {
        @NotNull
        private String name;
        @NotNull
        private String logoLink;
        @Min(1)
        private int carId;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseCreateBrand extends RequestCreateBrand {
        @NotNull
        private int id;
    }

}
