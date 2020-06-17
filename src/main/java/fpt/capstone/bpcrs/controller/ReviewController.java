package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Review;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.ReviewPayload;
import fpt.capstone.bpcrs.service.AccountService;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.service.ReviewService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @RolesAllowed({"USER", "ADMINISTRATOR"})
    public ResponseEntity<?> getReviews(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam int carId) {
        List<Review> reviews = reviewService.getAllReviewPaging(page, size, carId);
        List<ReviewPayload.ResponseCreateReview> reviewList = ObjectMapperUtils.mapAll(reviews,ReviewPayload.ResponseCreateReview.class);
        return ResponseEntity.ok(new ApiResponse<>(true, reviewList));
    }

    @PostMapping
    @RolesAllowed("USER")
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewPayload.RequestCreateReview request) {
        Car car = carService.getCarById(request.getCarId());
        if (car == null) {
            return new ResponseEntity(new ApiError("Car with id= " + request.getCarId() + " not found", ""),
                    HttpStatus.BAD_REQUEST);
        }
        ReviewPayload.ResponseCreateReview response = new ReviewPayload.ResponseCreateReview();
        Review newReview = (Review) new Review().buildObject(request, true);
        reviewService.createReview(newReview).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }
}
