package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
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
    private String from_date;

    @Column
    @NotNull
    private String to_date;

    @Column
    @NotNull
    private String destination;

    @Column
    @NotNull
    private String status;

    @Column
    @NotNull
    private String description;

    @Column
    private int rentPrice;

    @Column
    private int fixingPrice;

    @ManyToOne
    @JoinColumn(name = "renter_id")
    @ApiModelProperty(hidden = true)
    private Account renter;

    @ManyToOne
    @JoinColumn(name = "lessor_id")
    @ApiModelProperty(hidden = true)
    private Account lessor;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @ApiModelProperty(hidden = true)
    private Car car;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ApiModelProperty(hidden = true)
    private List<Agreement> agreements;
}
