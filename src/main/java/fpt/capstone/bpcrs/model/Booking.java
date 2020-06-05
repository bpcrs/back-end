package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.component.Auditing;
import fpt.capstone.bpcrs.payload.BookingPayload;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "booking")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
public class Booking extends Auditing {

    @JsonView(BookingPayload.Request_CreateBooking_Validate.class)
    @Column(nullable = false)
    private Date from_date;

    @JsonView(BookingPayload.Request_CreateBooking_Validate.class)
    @Column(nullable = false)
    private Date to_date;

    @JsonView(BookingPayload.Request_CreateBooking_Validate.class)
    @Column(nullable = false)
    private String destination;

    @JsonView(BookingPayload.Request_CreateBooking_Validate.class)
    private String status;

    @JsonView(BookingPayload.Request_CreateBooking_Validate.class)
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @NonNull
    @ApiModelProperty(hidden = true)
    private Account renter;

    @ManyToOne
    @NonNull
    @ApiModelProperty(hidden = true)
    private Account lessor;

    @ManyToOne
    @NonNull
    @ApiModelProperty(hidden = true)
    private Car car;

    public Booking buildCar() {
        return Booking.builder().car(car).lessor(lessor)
                .renter(renter).description(description).status(status)
                .destination(destination).from_date(from_date).to_date(to_date).build();
    }
}
