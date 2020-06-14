package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.CarPayload;
import fpt.capstone.bpcrs.service.BrandService;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.service.DappService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
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
    @Autowired
    private DappService dappService;
    @GetMapping
    public ResponseEntity<?> getCars(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false, defaultValue = "") String search) {
        List<Car> cars = carService.getAllCarPaging(page, size, search);
        List<CarPayload.ResponseGetCar> carList = ObjectMapperUtils.mapAll(cars,CarPayload.ResponseGetCar.class);
        return ResponseEntity.ok(new ApiResponse<>(true, carList));
    }

    @PostMapping
    public ResponseEntity<?> createCar(@Valid @RequestBody CarPayload.ResponseGetCar request) {
        Brand brand = brandService.getBrandById(request.getBrandId());
        if (brand == null){
            return new ResponseEntity(new ApiError("Brand with id=" + request.getBrandId() + " not found",""),HttpStatus.BAD_REQUEST);
        }
        CarPayload.ResponseGetCar response = new CarPayload.ResponseGetCar();
        Car newCar = (Car) new Car().buildObject(request, true);
        newCar.setBrand(brand);
        carService.createCar(newCar).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }
//
    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable() int id){
        CarPayload.ResponseGetCar response = new CarPayload.ResponseGetCar();
        Car car = carService.getCarById(id);
        if (car != null)  {
            car.buildObject(response,false);
            return ResponseEntity.ok(new ApiResponse<>(true,response));
        }
        return new ResponseEntity(new ApiResponse<>(false,"Not found car with id=" + id ), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable() int id, @RequestBody CarPayload.RequestUpdateCar request){
        Car car = carService.getCarById(id);
        if (car == null){
            return new ResponseEntity(new ApiError("Car with id=" + id + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        CarPayload.RequestUpdateCar response = new CarPayload.RequestUpdateCar();
        Car updateCar = (Car) new Car().buildObject(request, true);
        updateCar.setId(id);
        carService.updateCar(updateCar, id).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

}
