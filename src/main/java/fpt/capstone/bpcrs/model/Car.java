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
//@ToString(of = {"id"})
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ordinal")
public class  Car extends Auditing {

    @Column
    @NotNull
    @JsonView({CarPayload.Request_CreateCar_Validate.class})
    private String name;

    @Column
    @NotNull
    @JsonView(CarPayload.Request_CreateCar_Validate.class)
    private String model;

    @Column
    @JsonView(CarPayload.Request_CreateCar_Validate.class)
    private int seat;

    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean isAvailable;

    @Column
    @NotNull
    @JsonView(CarPayload.Request_CreateCar_Validate.class)
    private String sound;

    @Column
    @NotNull
    @JsonView(CarPayload.Request_CreateCar_Validate.class)
    private String screen;

    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    @JsonView(CarPayload.Request_CreateCar_Validate.class)
    private boolean autoDriver;

    @Column
    @NotNull
    @JsonView(CarPayload.Request_CreateCar_Validate.class)
    private String plateNum;

    @Column
    @NotNull
    @JsonView(CarPayload.Request_CreateCar_Validate.class)
    private String registrationNum;

    @ManyToOne
    @JoinColumn(name = "owner_id")
//    @JsonBackReference(value = "owner")
    @ApiModelProperty(hidden = true)
    private Account owner;

    @ManyToOne
    @ApiModelProperty(hidden = true)
//    @JsonBackReference(value = "brand_id")
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Car buildCar(){
        return Car.builder().brand(brand).model(model)
                .name(name).plateNum(plateNum).registrationNum(registrationNum)
                .screen(screen).seat(seat).sound(sound).build();
    }

    @ApiModelProperty(hidden = true)
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference(value = "carMs")
    private List<Booking> car;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference(value = "imageM")
    @ApiModelProperty(hidden = true)
    private List<Image> images;
}
