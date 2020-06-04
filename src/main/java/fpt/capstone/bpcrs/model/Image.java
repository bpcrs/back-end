package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.*;
import fpt.capstone.bpcrs.component.Auditing;
import fpt.capstone.bpcrs.payload.ImagePayload;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "image")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
//@ToString(of = {"id"})
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ordinal")
public class Image extends Auditing {

    @Column
    @NotNull
    @JsonView(ImagePayload.Request_CreateImage_Validate.class)
    private String link;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "car_id")
    private Car car;

    public Image buildImage() {
        return Image.builder().link(link).car(car)
                .build();
    }
}
