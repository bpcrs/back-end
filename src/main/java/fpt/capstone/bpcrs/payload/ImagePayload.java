package fpt.capstone.bpcrs.payload;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@NoArgsConstructor
public class ImagePayload {
    public interface Request_CreateImage_Validate {

    }
    public interface Request_GetImage_Response {

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateImage extends Image {
        @Min(1)
        @JsonView(Request_CreateImage_Validate.class)
        private int carId;
    }
}
