package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "car")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = {"id"})
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Car extends Auditing {

    @Column
    private String name;

    @Column
    private String model;

    @Column
    private int seat;

    @Column
    private boolean isAvaliable;

    @Column
    private String sound;

    @Column
    private String screen;

    @Column
    private boolean autoDriver;

    @Column
    private String plateNum;

    @Column
    private String registrationNum;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Account owner;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
