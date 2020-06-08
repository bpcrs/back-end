package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.*;
import fpt.capstone.bpcrs.component.Auditing;
import fpt.capstone.bpcrs.payload.ReviewPayload;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.modelmapper.ModelMapper;

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
