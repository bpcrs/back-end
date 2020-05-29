package fpt.capstone.bpcrs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.payload.AccountResponse;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.CarPayload;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.util.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car")
@Slf4j
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<?> getCars(@RequestParam int page, @RequestParam int size) {
        List<Car> cars = carService.getAllCarPaging(page, size);
        return ResponseEntity.ok(new ApiResponse<>(true, cars));
    }

    @PostMapping
    ResponseEntity<?> createCar(@JsonView(CarPayload.Request_CreateCar.class) @RequestBody Car newCar) {
        List<String> errors = ValidateUtils.validateObject(CarPayload.Request_CreateCar.class, newCar);
        if (errors.size() > 0) {
            throw new BadRequestException(errors.toString());
        }
//        Car car = carService.createCar(newCar);
//        return ResponseEntity.ok(new ApiResponse<>(true, car));
        return null;
    }


}
