package fpt.capstone.bpcrs.payload;

import fpt.capstone.bpcrs.constant.CarEnum;
import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class CarPayload {

    @Data
    @NoArgsConstructor
    public static class ResponseGetCar{
        @NotNull
        private String name;
        @NotNull
        private ModelPayload.ResponseCreateModel model;
        @Min(1)
        private int seat;
        @NotNull
        private String screen;
        @NotNull
        private String year;
        @NotNull
        private boolean autoDriver;
        @NotNull
        private String plateNum;
        private BrandPayload.ResponseCreateBrand brand;
        @NotNull
        private int id;
        private String VIN;
        private AccountPayload.AccountResponse owner;
        @NotNull
        private double price;
        private LocalDateTime createdDate;
        private String location;
        private CarEnum status;
        private List<ImagePayload.ResponseCreateImage> images;
    }

    @Data
    @NoArgsConstructor
    public static class ResponseFilterCar{
        @NotNull
        private String name;
        @NotNull
        private ModelPayload.ResponseCreateModel model;
        @Min(1)
        private int seat;
        @NotNull
        private String screen;
        @NotNull
        private String year;
        @NotNull
        private boolean autoDriver;
        @NotNull
        private String plateNum;
        private BrandPayload.ResponseCreateBrand brand;
        @NotNull
        private int id;
        private String VIN;
        private AccountPayload.AccountResponse owner;
        @NotNull
        private double price;
        private LocalDateTime createdDate;
        private String location;
        private CarEnum status;
        private List<ImagePayload.ResponseCreateImage> images;
        private String distance;
    }

    @Data
    @NoArgsConstructor
    public static class RequestUpdateCar{
        @NotNull
        private String name;
        @NotNull
        private int modelId;
        @Min(1)
        private int seat;
//        @NotNull
//        private String sound;
        @NotNull
        private String screen;
        @NotNull
        private String year;
        @NotNull
        private boolean autoDriver;
        @NotNull
        private String plateNum;
        private int brandId;
        @NotNull
        private int id;
        private String VIN;
        private int ownerId;
        @NotNull
        private double price;
        private CarEnum status;
        private String location;
    }

}
