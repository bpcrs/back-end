package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import fpt.capstone.bpcrs.payload.CarPayload;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "car")
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(of = "id", callSuper = false)
//@ToString(of = {"id"})
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ordinal")
public class Car extends Auditing {

    @Column
    @NotNull(message = "[name] parameter is missing", groups = CarPayload.Request_CreateCar.class)
    @JsonView(CarPayload.Request_CreateCar.class)
    private String name;

    @Column
    @NotNull(message = "[model] parameter is missing", groups = CarPayload.Request_CreateCar.class)
    @JsonView(CarPayload.Request_CreateCar.class)
    private String model;

    @Column
    @JsonView(CarPayload.Request_CreateCar.class)
    private int seat;

    @Column
    private boolean isAvaliable;

    @Column
    @JsonView(CarPayload.Request_CreateCar.class)
    private String sound;

    @Column
    @JsonView(CarPayload.Request_CreateCar.class)
    private String screen;

    @Column
    @JsonView(CarPayload.Request_CreateCar.class)
    private boolean autoDriver;

    @Column
    @JsonView(CarPayload.Request_CreateCar.class)
    private String plateNum;

    @Column
    @JsonView(CarPayload.Request_CreateCar.class)
    private String registrationNum;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Account owner;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
