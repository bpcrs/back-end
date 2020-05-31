package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.*;
import fpt.capstone.bpcrs.component.Auditing;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
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
    private String name;

    @Column
    private String logoLink;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    @JsonBackReference
    private Collection<Car> car;


}
