package fpt.capstone.bpcrs.payload;

import fpt.capstone.bpcrs.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private int id_booking;
    private String description;
    private Date from_date;
    private Date to_date;
    private String destination;
    private double price;

    public static BookingResponse setResponse(Booking booking) {
        return BookingResponse.builder()
                .id_booking(booking.getId())
                .description(booking.getDescription())
                .from_date(booking.getFrom_date())
                .to_date(booking.getTo_date())
                .price(booking.getPrice())
                .destination(booking.getDestination())
                .build();
    }
}
