package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "review")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ordinal")
public class Review extends Auditing {

    @Column
    @NotNull
    private int rating;

    @Column
    @NotNull
    private String comment;

    @ManyToOne
    @ApiModelProperty(hidden = true)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @ApiModelProperty(hidden = true)
    @JoinColumn(name = "account_id")
    private Account renter;
}
