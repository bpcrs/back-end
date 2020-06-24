package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Car;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CarService {

    Car createCar(Car newCar);

    Car getCarById(int id);

    Car updateCar(Car updateCar, int id);

    Page<Car> getAllCarsPagingByFilters(int page, int size, String model, Integer seat, Double fromPrice, Double toPrice, Integer brandId);
}
