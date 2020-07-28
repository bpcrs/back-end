package fpt.capstone.bpcrs.payload;

import fpt.capstone.bpcrs.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
public class ImagePayload {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateImage {
        @Min(1)
        private int carId;

        @NotNull
        private List<String> link;

//        private ImageTypeEnum type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseCreateImage {
        private int id;
        private String link;
        private Image type;
    }
}
