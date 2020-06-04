package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fpt.capstone.bpcrs.component.Auditing;
import fpt.capstone.bpcrs.constant.BookingEnum;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "booking")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
public class Booking extends Auditing {

    @ManyToOne
    @NonNull
    private Account renter;

    @ManyToOne
    @NonNull
    private Account lessor;

    @ManyToOne
    @NonNull
    private Car car;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Date from_date;

    @Column(nullable = false)
    private Date to_date;

    @Column(nullable = false)
    private String destination;

    @Enumerated(EnumType.STRING)
    private BookingEnum status;

    @Column(nullable = false)
    private String description;
}
