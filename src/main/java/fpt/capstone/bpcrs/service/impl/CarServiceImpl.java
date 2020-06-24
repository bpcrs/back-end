package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.IgnoreNullProperty;
import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.repository.CarRepository;
import fpt.capstone.bpcrs.service.CarService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Car> getAllCarPaging(int page, int size, String search) {
        Page<Car> cars = carRepository.findAllByNameContainsAndIsAvailableIsTrue(search,new Paging(page,size,Sort.unsorted()));
        return cars.get().collect(Collectors.toList());
    }

    @Override
    public Car createCar(Car newCar) {
        return carRepository.save(newCar);
    }

    @Override
    public Car getCarById(int id) {
        Optional<Car> car = carRepository.findById(id);
        return car.orElse(null);
    }

    @Override
    public Car updateCar(Car updateCar, int id) {
        Car car = carRepository.getOne(id);
        BeanUtils.copyProperties(updateCar, car, IgnoreNullProperty.getNullPropertyNames(updateCar));
        return carRepository.save(car);
    }

    @Override
    public int count() {
        return carRepository.countAllByIsAvailableIsTrue();
    }

}
