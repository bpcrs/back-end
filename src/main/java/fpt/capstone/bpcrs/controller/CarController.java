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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping
    public ResponseEntity<?> getCars(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false, defaultValue = "") String search) {
        List<Car> cars = carService.getAllCarPaging(page, size, search);
        return ResponseEntity.ok(new ApiResponse<>(true, cars));
    }

    @PostMapping
    public ResponseEntity<?> createCar(@JsonView(CarPayload.Request_CreateCar_Validate.class) @Valid @RequestBody CarPayload.RequestCreateCar request) {
        Brand brand = brandService.getBrandById(request.getBrandId());
        if (brand == null){
            return new ResponseEntity(new ApiError("Brand with id=" + request.getBrandId() + " not found",""),HttpStatus.BAD_REQUEST);
        }
        request.setBrand(brand);
        Car car = carService.createCar(request.buildCar());
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
    public ResponseEntity<?> updateCar(@PathVariable() int id, @RequestBody CarPayload.RequestUpdateCar request){
//        Brand brand = brandService.getBrandById(request.getBrandId());
//        if (brand == null){
//            return new ResponseEntity(new ApiError("Brand with id=" + request.getBrandId() + " not found", ""), HttpStatus.BAD_REQUEST);
//        }
//        request.setBrand(brand);
//        boolean isExsited = carService.getCarById(id) != null;
//        if (!isExsited){
//            return new ResponseEntity(new ApiError("Car with id=" + request.getBrandId() + " not found", ""), HttpStatus.BAD_REQUEST);
//        }
//        request.setId(id);
        Car car = carService.updateCar(request.buildCar(), id);
        return ResponseEntity.ok(new ApiResponse<>(true, car));
    }

}
