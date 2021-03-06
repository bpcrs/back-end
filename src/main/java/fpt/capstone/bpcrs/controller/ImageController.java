package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.ImageTypeEnum;
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
                                       @RequestParam int carId, @RequestParam ImageTypeEnum type) {
        Car car = carService.getCarById(carId);
        if (car == null) {
            return new ResponseEntity<>(new ApiError("Car with id = " + carId + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        Page<Image> images = imageService.getAllImagePaging(page, size, carId, type);
        List<ImagePayload.ResponseCreateImage> imageList = ObjectMapperUtils.mapAll(images.toList(), ImagePayload.ResponseCreateImage.class);
        PagingPayload pagingPayload =
                PagingPayload.builder().data(imageList).count((int) images.getTotalElements()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
    }


    @PostMapping
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> createImage(@Valid @RequestBody ImagePayload.RequestCreateImage request, @Valid @RequestParam ImageTypeEnum type) {

        Car car = carService.getCarById(request.getCarId());
        if (car == null) {
            return new ResponseEntity<>(new ApiError("Car with id = " + request.getCarId() + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        List<Image> newImages = new ArrayList<>();
        for (String link : request.getLink()) {
            Image image = Image.builder().car(car).link(link).type(type).build();
            newImages.add(image);
        }
        List<Image> images = imageService.createImages(newImages);
        List<ImagePayload.ResponseCreateImage> imageList = ObjectMapperUtils.mapAll(images, ImagePayload.ResponseCreateImage.class);
        return ResponseEntity.ok(new ApiResponse<>(true, imageList));
    }

    @PostMapping("/car/{id}")
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> createImages(@PathVariable() int id, @RequestBody ImagePayload.CreateImage requests) {
        Car car = carService.getCarById(id);
        if ( car == null) {
            return new ResponseEntity<>(new ApiError("Car with id = " + id + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        List<Image> newImages = new ArrayList<>();
        for (ImagePayload.RequestImage image : requests.getImages()) {
            Image img = Image.builder().car(car).link(image.getLink()).type(image.getType()).build();
            newImages.add(img);
        }
        List<Image> images = imageService.createImages(newImages);
        List<ImagePayload.ResponseCreateImage> responses = ObjectMapperUtils.mapAll(images, ImagePayload.ResponseCreateImage.class);
        return  ResponseEntity.ok(new ApiResponse<>(true, responses));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> deleteImage(@PathVariable() int id) {
        Image image = imageService.getImageById(id);
        if (image == null) {
            return new ResponseEntity<>(new ApiError("Image with id = " + id + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        imageService.deleteImage(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Delete success"));
    }

    @PutMapping("/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> changeImageType(@PathVariable() int id,@RequestParam ImageTypeEnum type) {
        Image image = imageService.getImageById(id);
        if (image == null) {
            return new ResponseEntity<>(new ApiError("Image with id = " + id + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        imageService.changeTypeImage(image, type);
        ImagePayload.ResponseCreateImage response = ObjectMapperUtils.map(image, ImagePayload.ResponseCreateImage.class);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }
}
