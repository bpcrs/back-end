package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Image;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.ImagePayload;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.service.ImageService;
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
@RequestMapping("/image")
@Slf4j
public class ImageController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private CarService carService;

    @GetMapping
    @RolesAllowed({"USER", "ADMINISTRATOR"})
    public ResponseEntity<?> getImages(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
                                       @RequestParam int carId) {
        Car car =  carService.getCarById(carId);
        if (car == null) {
            return new ResponseEntity<>(new ApiError("Car with id = " + carId + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        List<Image> images = imageService.getAllImagePaging(page, size, carId);
        List<ImagePayload.ResponseCreateImage> imageList = ObjectMapperUtils.mapAll(images,ImagePayload.ResponseCreateImage.class);
        return ResponseEntity.ok(new ApiResponse<>(true, imageList));
    }

    @PostMapping
    @RolesAllowed("USER")
    public ResponseEntity<?> createImage(@Valid @RequestBody ImagePayload.RequestCreateImage request) {
        Car car =  carService.getCarById(request.getCarId());
        if (car == null) {
            return new ResponseEntity<>(new ApiError("Car with id = " + request.getCarId() + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        ImagePayload.ResponseCreateImage response = new ImagePayload.ResponseCreateImage();
        Image newImage = (Image) new Image().buildObject(request, true);
        newImage.setCar(car);
        imageService.createImage(newImage).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }
}
