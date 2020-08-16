package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseBrand extends RequestCreateBrand {
        @NotNull
        private int id;
        @NotNull
        private String name;
        @NotNull
        private String logoLink;
    }


}
