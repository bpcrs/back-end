package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @Column
    @NotNull
    private double price;

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
