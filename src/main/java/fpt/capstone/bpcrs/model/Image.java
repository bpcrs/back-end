package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @JoinColumn(name = "car_id")
    private Car car;

    public Image buildImage() {
        return Image.builder().link(link).car(car)
                .build();
    }
}
