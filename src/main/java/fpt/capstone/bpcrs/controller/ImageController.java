package fpt.capstone.bpcrs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Image;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.ImagePayload;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class ImageController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<?> getImages(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
                                       @RequestParam int carId) {
        List<Image> images = imageService.getAllImagePaging(page, size, carId);
        return ResponseEntity.ok(new ApiResponse<>(true, images));
    }

    @PostMapping
    public ResponseEntity<?> createImage(@JsonView(ImagePayload.Request_CreateImage_Validate.class) @Valid @RequestBody ImagePayload.RequestCreateImage request) {
        Car car =  carService.getCarById(request.getCarId());
        if (car == null) {
            return new ResponseEntity<>(new ApiError("Car with id = " + request.getCarId() + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        request.setCar(car);
        Image image = imageService.createImage(request.buildImage());
        return ResponseEntity.ok(new ApiResponse<>(true, image));
    }
}
