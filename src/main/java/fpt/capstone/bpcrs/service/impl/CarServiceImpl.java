package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.IgnoreNullProperty;
import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Car_;
import fpt.capstone.bpcrs.model.specification.CarSpecification;
import fpt.capstone.bpcrs.repository.CarRepository;
import fpt.capstone.bpcrs.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

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
    public Page<Car> getAllCarsPagingByFilters(int page, int size, String[] models, Integer seat, Double fromPrice, Double toPrice, Integer brandId) {
        Specification conditon = (Specification) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Car_.IS_AVAILABLE), true);
        if (models != null) {
            conditon = conditon.and(CarSpecification.carHasModelName(models));
        }
        if (seat != null) {
            conditon = conditon.and(CarSpecification.carHasSeatNumber(seat));
        }
        if (toPrice != null && fromPrice != null) {
            conditon = conditon.and(CarSpecification.carHasFromPriceTpPrice(fromPrice, toPrice));
        }
        if (brandId != null) {
            conditon = conditon.and(CarSpecification.carHasBrand(brandId));
        }
        Page<Car> cars = carRepository.findAll(conditon,new Paging(page,size,Sort.unsorted()));
        return cars;
    }

}
