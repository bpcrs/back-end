package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Table(name = "brand")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
//@ToString(of = {"id"})
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ordinal")
public class Brand extends Auditing {

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String logoLink;

    @OneToMany(mappedBy = "brand")
    @ApiModelProperty(hidden = true)
    private List<Car> cars;

    public Brand buildBrand() {
        return Brand.builder().name(name).logoLink(logoLink)
                .build();
    }

}
