package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Review;
import fpt.capstone.bpcrs.repository.AccountRepository;
import fpt.capstone.bpcrs.repository.BookingRepository;
import fpt.capstone.bpcrs.repository.ReviewRepository;
import fpt.capstone.bpcrs.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private AccountRepository accountRepository;

//    @Override
//    public List<Review> getAllReviewPaging(int page, int size, int carId) {
//        Page<Review> reviews = reviewRepository.findAllByCar_Id(carId, new Paging(page, size, Sort.unsorted()));
//        return reviews.get().collect(Collectors.toList());
//    }
    @Override
    public Page<Review> getAllReviewPaging(int page, int size, int carId) {
        Page<Review> reviews = reviewRepository.findAllByCar_Id(carId, new Paging(page, size, Sort.unsorted()));
        return reviews;
    }

    @Override
    public Review createReview(Review newReview) {
        return reviewRepository.save(newReview);
    }

    @Override
    public Review getReviewById(int id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElse(null);
    }

    @Override
    public Boolean checkBookingCanReview(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if(booking != null){
            String statusCheck = booking.getStatus().toString();
            if(statusCheck.contains("DONE")){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public Boolean checkBookingIsReviewYet(int carId, int renterId) {
//        List<Review> reviews = reviewRepository.findAll();
//        for (int i = 0; i < reviews.size(); i++){
//            if(reviews.get(i).getRenter().getId() == renterId){
//                if(reviews.get(i).getCar().getId() == carId){
//                    return true;
//                }
//            }
//        }
//        return false;
        Review review = reviewRepository.findReviewByCarIdAndRenterId(carId, renterId);
        if(review != null){
            return true;
        }
        return false;
    }
}
