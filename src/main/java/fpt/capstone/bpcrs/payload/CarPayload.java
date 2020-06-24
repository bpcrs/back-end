package fpt.capstone.bpcrs.payload;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CarPayload {

//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class RequestCreateCar {
//
//        @Min(1)
//        private int brandId;
//    }

//    @EqualsAndHashCode(callSuper = true)


    @Data
    @NoArgsConstructor
    public static class ResponseGetCar{
        @NotNull
        private String name;
        @NotNull
        private String model;
        @Min(1)
        private int seat;
        @NotNull
        private String sound;
        @NotNull
        private String screen;
        @NotNull
        private boolean autoDriver;
        @NotNull
        private String plateNum;
        @NotNull
        private int id;
        @NotNull
        private int brandId;
        @NotNull
        private String VIN;
        @NotNull
        private double price;
    }

    @Data
    @NoArgsConstructor
    public static class RequestUpdateCar{
        private String name;
        private String model;
        @Min(1)
        private int seat;
        private boolean isAvailable;
        private String sound;
        private String screen;
        private boolean autoDriver;
        private String plateNum;
        private double price;
    }

}
