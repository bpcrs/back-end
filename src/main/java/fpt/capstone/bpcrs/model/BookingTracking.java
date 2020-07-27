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

@Entity
@Data
@Table(name = "booking_tracking")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(
        value = {"lastModifiedDate"},
        allowGetters = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ordinal")
public class BookingTracking extends Auditing {

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private BookingEnum status;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @ApiModelProperty(hidden = true)
    @ToString.Exclude
    private Booking booking;
}
