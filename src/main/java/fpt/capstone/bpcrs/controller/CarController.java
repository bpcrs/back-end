package fpt.capstone.bpcrs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.CarPayload;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.util.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/car")
@Slf4j
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<?> getCars(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        List<Car> cars = carService.getAllCarPaging(page, size);
        return ResponseEntity.ok(new ApiResponse<>(true, cars));
    }

    @PostMapping
    public ResponseEntity<?> createCar(@JsonView(CarPayload.Request_CreateCar.class) @Valid @RequestBody CarPayload.Request newCar) {
//        Car cars = carService.createCar(newCar);
//        return ResponseEntity.ok(new ApiResponse<>(true, cars));
        return ResponseEntity.ok(new ApiResponse<>(true, newCar));
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
    public ResponseEntity<?> updateCar(@PathVariable int id, @RequestBody Car updateCar){
        return null;
    }

}
