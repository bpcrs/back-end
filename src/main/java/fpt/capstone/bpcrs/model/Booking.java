package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "booking")
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ordinal")
public class Booking extends Auditing {

    @Column(nullable = false)
    private Date from_date;

    @Column(nullable = false)
    private Date to_date;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String description;

    @Column
    private int rentPrice;

    @Column
    private int fixingPrice;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "renter_id")
    @ApiModelProperty(hidden = true)
    private Account renter;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "lessor_id")
    @ApiModelProperty(hidden = true)
    private Account lessor;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "car_id")
    @ApiModelProperty(hidden = true)
    private Car car;

//    public Booking buildBooking() {
//        return Booking.builder().car(car).lessor(lessor)
//                .renter(renter).description(description).status(status)
//                .destination(destination).from_date(from_date).to_date(to_date).build();
//    }

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ApiModelProperty(hidden = true)
    private List<Agreement> agreements;
}
