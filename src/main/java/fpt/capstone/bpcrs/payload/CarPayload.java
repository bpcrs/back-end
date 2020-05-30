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
    public interface Request_CreateCar{

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request extends Car {
        @Min(1)
        @JsonView(CarPayload.Request_CreateCar.class)
        private int brand_id;
    }

}
