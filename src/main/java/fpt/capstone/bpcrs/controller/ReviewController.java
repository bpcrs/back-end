package fpt.capstone.bpcrs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Review;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.ReviewPayload;
import fpt.capstone.bpcrs.service.AccountService;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<?> getReviews(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
                                        @RequestParam int carId) {
        List<Review> reviews = reviewService.getAllReviewPaging(page, size, carId);
        return ResponseEntity.ok(new ApiResponse<>(true, reviews));
    }

    @PostMapping
    public ResponseEntity<?> createReview(@JsonView(ReviewPayload.Request_CreateReview_Validate.class) @Valid @RequestBody ReviewPayload.RequestCreateReview request) {
        Car car = carService.getCarById(request.getCarId());
        if (car == null) {
            return new ResponseEntity(new ApiError("Car with id= " + request.getCarId() + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        request.setCar(car);
        Review review = reviewService.createReview(request.buildReview());
        return ResponseEntity.ok(new ApiResponse<>(true, review));
    }


}
