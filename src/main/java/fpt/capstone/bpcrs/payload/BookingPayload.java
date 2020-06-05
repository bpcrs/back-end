package fpt.capstone.bpcrs.payload;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@NoArgsConstructor
public class BookingPayload {
    public interface Request_CreateBooking_Validate {
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCreateBooking extends Booking {
        @Min(1)
        @JsonView(BookingPayload.Request_CreateBooking_Validate.class)
        private int carId;


    }
}
