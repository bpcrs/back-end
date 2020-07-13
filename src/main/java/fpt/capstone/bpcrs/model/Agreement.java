package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "agreement")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
//@ToString(of = {"id"})
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ordinal")
public class Agreement extends Auditing {

    @Column
    @NotNull
    private  String name;

    @Column
    @NotNull
    private double value;

    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @ApiModelProperty(hidden = true)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "criteria_id")
    @ApiModelProperty(hidden = true)
    private Criteria criteria;



}
