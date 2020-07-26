package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import fpt.capstone.bpcrs.constant.BookingEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Column
    @NotNull
    private Date from_date;

    @Column
    @NotNull
    private Date to_date;

    @Column
    @NotNull
    private String destination;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private BookingEnum status;

    @Column
    @NotNull
    private String location;

    @Column
    @NotNull
    private double totalPrice;


    @ManyToOne
    @JoinColumn(name = "renter_id")
    @ApiModelProperty(hidden = true)
    @ToString.Exclude
    private Account renter;


    @ManyToOne
    @JoinColumn(name = "car_id")
    @ApiModelProperty(hidden = true)
    @ToString.Exclude
    private Car car;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ApiModelProperty(hidden = true)
    @ToString.Exclude
    private List<Agreement> agreements;
}
