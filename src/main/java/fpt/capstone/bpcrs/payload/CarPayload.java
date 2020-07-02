package fpt.capstone.bpcrs.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
public class CarPayload {

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
        private boolean isAvailable;
        @NotNull
        private String plateNum;
        private int brandId;
        @NotNull
        private int id;
        private String VIN;
        private AccountPayload.AccountResponse owner;
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
