package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class MapsPayload {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestDistanceBetweenLocation{
        @NotNull
        private String location;

        @NotNull
        private String destination;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseDistanceBetweenLocation{
        @NotNull
        private String distance;
    }


}
