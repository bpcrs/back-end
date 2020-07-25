package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.constant.CarEnum;
import fpt.capstone.bpcrs.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CarService {

//    boolean checkCarVin(Car car) throws JSONException, ParseException;

    Car createCar(Car newCar);

    Car getCarById(int id);

    Car updateCar(Car updateCar, int id);

    Page<Car> getAllCarsPagingByFilters(int page, int size, Integer[] modelId, Integer[] seat, Double fromPrice, Double toPrice, Integer[] brandId, Integer ownerId);

    Page<Car> getAllCarsByOwnerId(int ownerId, int page, int size);


    Page<Car> getAllCars(int page, int size);

    Car updateCarStatus(Car car, CarEnum status);

    boolean checkStatusCarBySM (CarEnum currentStatus, CarEnum nextStatus);

    Page<Car> getAllCarsByAvailable(boolean isAvailable, int page, int size);

}
