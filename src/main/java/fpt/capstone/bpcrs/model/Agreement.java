package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.*;
import fpt.capstone.bpcrs.component.Auditing;
import fpt.capstone.bpcrs.payload.AgreementPayload;
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
    @JsonView(AgreementPayload.Request_CreateAgreement_Validate.class)
    private String value;

    @Column
    @NotNull
    @JsonView(AgreementPayload.Request_CreateAgreement_Validate.class)
    private String status;

    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    @JsonView(AgreementPayload.Request_CreateAgreement_Validate.class)
    private boolean isApproved;

    @ManyToOne
//    @JsonBackReference(value = "booking_id")
    @JoinColumn(name = "booking_id")
    @ApiModelProperty(hidden = true)
    private Booking booking;

    @ManyToOne
//    @JsonBackReference(value = "criteria_id")
    @JoinColumn(name = "criteria_id")
    @ApiModelProperty(hidden = true)
    private Criteria criteria;

    public Agreement buildAgreement() {
        return Agreement.builder().value(value).status(status).isApproved(isApproved)
                .booking(booking).criteria(criteria).build();
    }

}
