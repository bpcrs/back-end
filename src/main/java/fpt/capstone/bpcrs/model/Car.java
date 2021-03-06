package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import fpt.capstone.bpcrs.constant.CarEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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
public class Car extends Auditing {

    @Column
    @NotNull
    private String name;

    @Column
    private String year;

    @Column
    private int seat;

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

    @Column
    @NotNull
    private double price;

    @Column
    private String location;

    @Column(columnDefinition = "int default 0")
    private int odometer;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarEnum status;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @ApiModelProperty(hidden = true)
    private Account owner;

    @ManyToOne
    @ApiModelProperty(hidden = true)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @ApiModelProperty(hidden = true)
    @JoinColumn(name = "model_id")
    private Model model;

    @ApiModelProperty(hidden = true)
    @OneToMany(mappedBy = "car")
    private List<Booking> car;

    @OneToMany(mappedBy = "car")
    @ApiModelProperty(hidden = true)
    private List<Image> images;

}
