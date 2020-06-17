package fpt.capstone.bpcrs.payload;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ImagePayload {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateImage{
        @Min(1)
        private int carId;

        @NotNull
        private String link;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseCreateImage extends RequestCreateImage{
        private int id;
    }
}
