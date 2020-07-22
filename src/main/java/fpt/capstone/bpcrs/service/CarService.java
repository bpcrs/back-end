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

    Page<Car> getAllCarsPagingByFilters(int page, int size, Integer[] modelId, Integer[] seat, Double fromPrice, Double toPrice, Integer[] brandId);

    List<Car> getAllCarsByOwnerId(int ownerId);

    Car updateCarStatus(Car car, CarEnum status);

    boolean checkStatusCarBySM (CarEnum currentStatus, CarEnum nextStatus);
}
