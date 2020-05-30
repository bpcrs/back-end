package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Car;

import java.util.List;

public interface CarService {
    List<Car> getAllCarPaging(int page, int size, String search);

    Car createCar(Car newCar);

    Car updateCar(Car updateCar);

    Car getCarById(int id);

}
