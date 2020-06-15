package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.*;
import fpt.capstone.bpcrs.component.Auditing;
import fpt.capstone.bpcrs.payload.CarPayload;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "car")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ordinal")
public class  Car extends Auditing {

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String model;

    @Column
    private int seat;

    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean isAvailable;

    @Column
    @NotNull
    private String sound;

    @Column
    @NotNull
    private String screen;

    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean autoDriver;

    @Column
    @NotNull
    private String plateNum;

    @Column
    @NotNull
    private String VIN;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @ApiModelProperty(hidden = true)
    private Account owner;

    @ManyToOne
    @ApiModelProperty(hidden = true)
    @JoinColumn(name = "brand_id")
    private Brand brand;


    @ApiModelProperty(hidden = true)
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> car;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ApiModelProperty(hidden = true)
    private List<Image> images;


}
