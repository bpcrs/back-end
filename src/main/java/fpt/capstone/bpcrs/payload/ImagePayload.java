package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
public class ImagePayload {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateImage{
        @Min(1)
        private int carId;

        @NotNull
        private List<String> link;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseCreateImage {
        private int id;
        private String link;
    }


}
