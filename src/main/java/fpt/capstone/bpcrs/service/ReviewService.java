package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviewPaging(int page, int size, int carId);

    Review createReview(Review newReview);

    Review getReviewById(int id);
}
