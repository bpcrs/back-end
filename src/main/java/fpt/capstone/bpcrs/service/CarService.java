package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.repository.CarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarService {
    List<Car> getAllCarPaging(int page, int size);

    Car createCar(Car newCar);

    Car updateCar(Car updateCar);

    Car getCarById(int id);

}
