package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private int id_renter;
    private int id_lessor;
    private int id_car;
    private String description;
    private Date from_date;
    private Date to_date;
    private String destination;
    private double price;
}
