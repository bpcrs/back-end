package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Image;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.ImagePayload;
import fpt.capstone.bpcrs.payload.PagingPayload;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.service.ImageService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<?> getImages(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
                                       @RequestParam int carId) {
        Car car = carService.getCarById(carId);
        if (car == null) {
            return new ResponseEntity<>(new ApiError("Car with id = " + carId + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        Page<Image> images = imageService.getAllImagePaging(page, size, carId);
        List<ImagePayload.ResponseCreateImage> imageList = ObjectMapperUtils.mapAll(images.toList(), ImagePayload.ResponseCreateImage.class);
        PagingPayload pagingPayload =
                PagingPayload.builder().data(imageList).count((int) images.getTotalElements()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
    }


    @PostMapping
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> createImage(@Valid @RequestBody ImagePayload.RequestCreateImage request) {

        Car car = carService.getCarById(request.getCarId());
        if (car == null) {
            return new ResponseEntity<>(new ApiError("Car with id = " + request.getCarId() + " not found", ""), HttpStatus.BAD_REQUEST);
        }
//        List<ImagePayload.ResponseCreateImage> responses = new ArrayList<>();
        List<Image> newImages = new ArrayList<>();
        for (String link : request.getLink()) {
            Image image = Image.builder().car(car).link(link).build();
            newImages.add(image);
        }
        List<Image> images = imageService.createImages(newImages);
        List<ImagePayload.ResponseCreateImage> imageList = ObjectMapperUtils.mapAll(images, ImagePayload.ResponseCreateImage.class);
        return ResponseEntity.ok(new ApiResponse<>(true, imageList));
    }
}
