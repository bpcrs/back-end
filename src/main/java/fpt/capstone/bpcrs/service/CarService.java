package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Car;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Page;

public interface CarService {

    boolean checkCarVin(Car car) throws JSONException, ParseException;

    Car createCar(Car newCar);

    Car getCarById(int id);

    Car updateCar(Car updateCar, int id);

    Page<Car> getAllCarsPagingByFilters(int page, int size, Integer[] modelId, Integer[] seat, Double fromPrice, Double toPrice, Integer[] brandId);
}
