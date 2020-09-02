package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import fpt.capstone.bpcrs.constant.BookingEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "booking")
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ordinal")
public class Booking extends Auditing {

    @Column
    @NotNull
    private LocalDateTime fromDate;

    @Column
    @NotNull
    private LocalDateTime toDate;

    @Column
    @NotNull
    private String destination;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private BookingEnum status;

    @Column
    @NotNull
    private String location;

    @Column
    @NotNull
    private double rentalPrice;

    @Column
    @NotNull
    private double totalPrice;

    @Column(columnDefinition = "int default 0")
    private int distance;

    @ManyToOne
    @JoinColumn(name = "renter_id")
    @ApiModelProperty(hidden = true)
    @ToString.Exclude
    private Account renter;


    @ManyToOne
    @JoinColumn(name = "car_id")
    @ApiModelProperty(hidden = true)
    @ToString.Exclude
    private Car car;

    @OneToMany(mappedBy = "booking")
    @ApiModelProperty(hidden = true)
    @ToString.Exclude
    private List<Agreement> agreements;

    @OneToMany(mappedBy = "booking")
    @ApiModelProperty(hidden = true)
    @ToString.Exclude
    private List<BookingTracking> trackings;

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        return getCar().getId() + getCar().getOwner().getEmail() + getRenter().getEmail() + getFromDate() + getToDate() + decimalFormat.format(rentalPrice) + decimalFormat.format(getCar().getPrice()) + getLocation() + getDestination();
    }

    public JSONArray agreementsToJSONArray() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Agreement agreement : getAgreements()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", agreement.getValue());
            jsonObject.put("name", agreement.getCriteria().getName());
            jsonObject.put("isApprove", agreement.isApproved());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
}

