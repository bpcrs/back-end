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
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    public boolean checkCarVin(Car car) throws JSONException {
        final String uri = "http://api.carmd.com/v3.0/decode?vin=" + car.getVIN();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("authorization", "Basic ZDYxYzBlYTgtYjk3YS00ZDBkLTkxMzEtNzQ5MDc2MDFhNzZi");
        headers.add("partner-token", "54cf08a2145e4c618303d7f77db7fb1f");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject.getJSONObject("message").getString("credentials").equals("valid");
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
    public Page<Car> getAllCarsPagingByFilters(int page, int size, Integer[] modelIds, Integer[] seat, Double fromPrice, Double toPrice, Integer[] brandIds) {
        Specification conditon = (Specification) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Car_.IS_AVAILABLE), true);
        if (modelIds != null && modelIds.length != 0) {
            conditon = conditon.and(CarSpecification.carHasModelName(modelIds));
        }
        if (seat != null && seat.length != 0) {
            conditon = conditon.and(CarSpecification.carHasSeatNumber(seat));
        }
        if (toPrice != null && fromPrice != null) {
            conditon = conditon.and(CarSpecification.carHasFromPriceTpPrice(fromPrice, toPrice));
        }
        if (brandIds != null && brandIds.length != 0) {
            conditon = conditon.and(CarSpecification.carHasBrand(brandIds));
        }
        Page<Car> cars = carRepository.findAll(conditon, new Paging(page, size, Sort.unsorted()));
        return cars;
    }

    @Override
    public List<Car> getAllCarsByOwnerId(int ownerId) {
//        List<Car> cars = new ArrayList<>();
        List<Car> carList = carRepository.findAllByOwner_Id(ownerId);
//        cars = BeanUtils.copyProperties();
        return carRepository.saveAll(carList);
    }

}
