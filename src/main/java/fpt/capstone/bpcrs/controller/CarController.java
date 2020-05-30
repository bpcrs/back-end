package fpt.capstone.bpcrs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.CarPayload;
import fpt.capstone.bpcrs.service.BrandService;
import fpt.capstone.bpcrs.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/car")
@Slf4j
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> getCars(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String search) {
//        if (search != null){
//
//        }
        List<Car> cars = carService.getAllCarPaging(page, size, search);
        return ResponseEntity.ok(new ApiResponse<>(true, cars));
    }

    @PostMapping
    public ResponseEntity<?> createCar(@JsonView(CarPayload.Request_CreateCar_Validate.class) @Valid @RequestBody CarPayload.RequestCreateCar request) {
        Brand brand = brandService.getBrandById(request.getBrandId());
        if (brand == null){
            return ResponseEntity.ok(new ApiError("Brand with id=" + request.getBrandId() + " not found", ""));
        }
        Car newCar = Car.builder().brand(brand).model(request.getModel())
                .name(request.getName()).plateNum(request.getPlateNum()).registrationNum(request.getRegistrationNum())
                .screen(request.getScreen()).seat(request.getSeat()).sound(request.getSound()).build();
        Car car = carService.createCar(newCar);
        return ResponseEntity.ok(new ApiResponse<>(true, car));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable() int id){
        Car car = carService.getCarById(id);
        if (car != null)  {
            return ResponseEntity.ok(new ApiResponse<>(true,car));
        }
        return new ResponseEntity(new ApiResponse<>(false,"Not found car with id=" + id ), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable int id, @JsonView(CarPayload.Request_CreateCar_Validate.class) @RequestBody CarPayload.RequestCreateCar request){

        return null;
    }

}
