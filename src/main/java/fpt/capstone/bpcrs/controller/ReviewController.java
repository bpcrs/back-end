package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Review;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.PagingPayload;
import fpt.capstone.bpcrs.payload.ReviewPayload;
import fpt.capstone.bpcrs.service.AccountService;
import fpt.capstone.bpcrs.service.BookingService;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.service.ReviewService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CarService carService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<?> getReviews(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam int carId) {
//        List<Review> reviews = reviewService.getAllReviewPaging(page, size, carId);
//        List<ReviewPayload.ResponseCreateReview> reviewList = ObjectMapperUtils.mapAll(reviews,ReviewPayload.ResponseCreateReview.class);
//        return ResponseEntity.ok(new ApiResponse<>(true, reviewList));
        Page<Review> reviews = reviewService.getAllReviewPaging(page, size, carId);
        if (reviews.isEmpty()) {
            return new ResponseEntity(new ApiError("System don't have any review", ""), HttpStatus.BAD_REQUEST);
        }else{
            List<ReviewPayload.ResponseCreateReview> reviewList
                    = ObjectMapperUtils.mapAll(reviews.toList(),ReviewPayload.ResponseCreateReview.class);

            PagingPayload pagingPayload =
                    PagingPayload.builder().data(reviewList).count((int) reviews.getTotalElements()).build();

            return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
        }

    }

    @PostMapping
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewPayload.RequestCreateReview request) {
        RoleEnum.USER.toString();
        Car car = carService.getCarById(request.getCarId());
        if (car == null) {
            return new ResponseEntity(new ApiError("Car with id= " + request.getCarId() + " not found", ""),
                    HttpStatus.BAD_REQUEST);
        }
        boolean checkBookingCanReview = reviewService.checkBookingCanReview(request.getBookingId());
        Booking booking = bookingService.getBookingInformation(request.getBookingId());
        if (booking == null) {
            return new ResponseEntity(new ApiError("Booking with id= " + request.getBookingId() + " not found", ""),
                    HttpStatus.BAD_REQUEST);
        }
        if(booking.getStatus() == BookingEnum.DONE){

            if (car.getOwner().getId() == accountService.getCurrentUser().getId()) {
                return new ResponseEntity(new ApiError("You can review your car", ""),
                        HttpStatus.BAD_REQUEST);
            } else  {
                if(reviewService.checkUserCanReview(booking.getId(), accountService.getCurrentUser().getId())){
                    ReviewPayload.ResponseCreateReview response = new ReviewPayload.ResponseCreateReview();
                    Review newReview = (Review) new Review().modelMaplerToObject(request, true);
                    newReview.setCar(car);

                    newReview.setRenter(accountService.getCurrentUser());
                    newReview.setBooking(booking);
                    reviewService.createReview(newReview).modelMaplerToObject(response, false);
                    return ResponseEntity.ok(new ApiResponse<>(true, response));
                }else{
                    return new ResponseEntity(new ApiError("this car have already rated", ""),
                            HttpStatus.BAD_REQUEST);
                }
            }
        }else{
            return new ResponseEntity(new ApiError("Your booking not done yet, please rating later", ""),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
