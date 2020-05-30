package fpt.capstone.bpcrs.payload;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.model.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@NoArgsConstructor
public class CarPayload {
    public interface Request_CreateCar_Validate {

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateCar extends Car {
        @Min(1)
        @JsonView(Request_CreateCar_Validate.class)
        private int brandId;

    }

}
