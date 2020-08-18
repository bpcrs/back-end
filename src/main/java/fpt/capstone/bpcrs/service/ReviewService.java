package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Review;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
//    List<Review> getAllReviewPaging(int page, int size, int carId);
    Page<Review> getAllReviewPaging(int page, int size, int carId);

    Review createReview(Review newReview);

    Review getReviewById(int id);

    Boolean checkBookingCanReview(int bookingId);

    Boolean checkBookingIsReviewYet(int carId, int renterId);

    Boolean checkUserCanReview(int bookingId, int renterId);


}
