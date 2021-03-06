package fpt.capstone.bpcrs.payload;

import fpt.capstone.bpcrs.constant.ImageTypeEnum;
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
    public static class RequestCreateImage{
        @Min(1)
        private int carId;

        @NotNull
        private List<String> link;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestImage{
//        @Min(1)
//        private int carId;

        @NotNull
        private String link;

        @NotNull
        private ImageTypeEnum type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateImage{
        @NotNull
        private List<RequestImage> images;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseCreateImage {
        private int id;
        private String link;
        private ImageTypeEnum type;
    }


}
