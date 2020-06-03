package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import fpt.capstone.bpcrs.payload.ReviewPayload;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "review")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ordinal")
public class Review extends Auditing {

    @Column
    @NotNull
    @JsonView(ReviewPayload.Request_CreateReview_Validate.class)
    private int rating;

    @Column
    @NotNull
    @JsonView(ReviewPayload.Request_CreateReview_Validate.class)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account renter;

    public Review buildReview() {
        return Review.builder().car(car).renter(renter).rating(rating)
                .comment(comment).build();
    }

}
