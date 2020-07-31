package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.CarEnum;
import fpt.capstone.bpcrs.constant.ImageTypeEnum;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Image;
import fpt.capstone.bpcrs.model.Model;
import fpt.capstone.bpcrs.payload.*;
import fpt.capstone.bpcrs.service.*;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/car")
@Slf4j
@Validated
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<?> getCars(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(required = false) Integer[] models,
                                     @RequestParam(required = false) Integer[] seat,
                                     @RequestParam(required = false) Double fromPrice,
                                     @RequestParam(required = false) Double toPrice,
                                     @RequestParam(required = false) Integer[] brand

    ) {
        List<Car> responses = new ArrayList<>();
        Page<Car> cars = carService.getAllCarsPagingByFilters(page, size, models, seat, fromPrice, toPrice, brand, accountService.getCurrentUser().getId());
        for (Car car : cars) {
            CarPayload.ResponseGetCar response = new CarPayload.ResponseGetCar();
            List<Image> images = imageService.getAllImage(car.getId(), ImageTypeEnum.CAR);
            car.setImages(images);
            car.buildObject(response, false);
            responses.add(car);
        }
        List<CarPayload.ResponseGetCar> carList = ObjectMapperUtils.mapAll(responses,
                CarPayload.ResponseGetCar.class);
        PagingPayload pagingPayload =
                PagingPayload.builder().data(carList).count((int) cars.getTotalElements()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
    }

    @PostMapping
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> createCar(@Valid @RequestBody CarPayload.RequestUpdateCar request) {
        Brand brand = brandService.getBrandById(request.getBrandId());
        Model model = modelService.getModelById(request.getModelId());
        if (brand == null) {
            return new ResponseEntity(new ApiError("Brand with id=" + request.getBrandId() + " not found", ""),
                    HttpStatus.BAD_REQUEST);
        }
        if (model == null) {
            return new ResponseEntity<>(new ApiError("Model  with id=" + request.getModelId() + " not found", ""),
                    HttpStatus.BAD_REQUEST);
        }
        CarPayload.ResponseGetCar response = new CarPayload.ResponseGetCar();
        Car newCar = (Car) new Car().buildObject(request, true);
        newCar.setBrand(brand);
        newCar.setModel(model);
        newCar.setOwner(accountService.getCurrentUser());
        newCar.setStatus(CarEnum.UNAVAILABLE);
        //check car VIN API (limit 25/month)
//        try {
//            if (!carService.checkCarVin(newCar)) {
//                throw new JSONException("VIN car is not valid!");
//            }
//        } catch (JSONException | ParseException e) {
//            return new ResponseEntity<>(new ApiError(e.toString(), "Car cannot created!"),
//                    HttpStatus.BAD_REQUEST);
//        }
        carService.createCar(newCar).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable() int id) {
        CarPayload.ResponseGetCar response = new CarPayload.ResponseGetCar();
        Car car = carService.getCarById(id);
        if (car != null) {
            car.buildObject(response, false);
            return ResponseEntity.ok(new ApiResponse<>(true, response));
        }
        return new ResponseEntity(new ApiResponse<>(false, "Car with id=" + id + " not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/owner/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getCarsByOwner(@PathVariable() int id,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        List<Car> responses = new ArrayList<>();
        Page<Car> cars = carService.getAllCarsByOwnerId(id, page, size);
        if (cars.isEmpty()) {
            return new ResponseEntity(new ApiError("User dont have any car", ""), HttpStatus.BAD_REQUEST);
        }
        for (Car car : cars) {
            responses.add(car);
        }
        List<CarPayload.ResponseGetCar> carList = ObjectMapperUtils.mapAll(responses,
                CarPayload.ResponseGetCar.class);
        PagingPayload pagingPayload =
                PagingPayload.builder().data(carList).count((int) cars.getTotalElements()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
    }

    @PutMapping("/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> updateCar(@PathVariable() int id, @RequestBody CarPayload.RequestUpdateCar request) {
        Car car = carService.getCarById(id);
        if (car == null) {
            return new ResponseEntity(new ApiError("Car with id=" + id + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        CarPayload.RequestUpdateCar response = new CarPayload.RequestUpdateCar();
        Car updateCar = (Car) new Car().buildObject(request, true);
        updateCar.setId(id);
        carService.updateCar(updateCar, id).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }


    @GetMapping("/admin")
    @RolesAllowed({RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getCarsAdmin(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size
                                    ) {
        Page<Car> cars = carService.getAllCars(page, size);
        if (cars.isEmpty()) {
            return new ResponseEntity(new ApiError("System don't have any car", ""), HttpStatus.BAD_REQUEST);
        }
        List<CarPayload.ResponseGetCar> carList = ObjectMapperUtils.mapAll(cars.toList(),
                CarPayload.ResponseGetCar.class);

        PagingPayload pagingPayload =
                PagingPayload.builder().data(carList).count((int) cars.getTotalElements()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
    }
    @PutMapping("/status/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> updateStatus(@PathVariable() int id, @Valid @RequestParam CarEnum status) {
        Car car = carService.getCarById(id);
        if (car == null) {
            return new ResponseEntity(new ApiError("Car with id=" + id + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        if (!carService.checkStatusCarBySM(car.getStatus(), status)) {
            return new ResponseEntity(new ApiError("Next status is not available " + status, ""), HttpStatus.BAD_REQUEST);
        }
        Car updateCar = carService.updateCarStatus(car, status);
        CarPayload.ResponseGetCar response = ObjectMapperUtils.map(updateCar, CarPayload.ResponseGetCar.class);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

//    @GetMapping("/available")
//    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
//    public ResponseEntity<?> getCarsByAvailable(@RequestParam(value = "isAvailable", required = false, defaultValue = "true") boolean isAvailable,
//                                                @RequestParam(defaultValue = "1") int page,
//                                                @RequestParam(defaultValue = "10") int size) {
//        List<Car> responses = new ArrayList<>();
//        Page<Car> cars = carService.getAllCarsByAvailable(isAvailable, page, size);
//        for (Car car : cars) {
//            Page<Image> images = imageService.getAllImagePaging(page, size, car.getId(), ImageTypeEnum.CAR);
//            car.setImages(images.toList());
//            responses.add(car);
//        }
//        List<CarPayload.ResponseGetCar> carList = ObjectMapperUtils.mapAll(responses,
//                CarPayload.ResponseGetCar.class);
//        PagingPayload pagingPayload =
//                PagingPayload.builder().data(carList).count((int) cars.getTotalElements()).build();
//        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
//    }


}
