package fpt.capstone.bpcrs.model.specification;

import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Car_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

public class CarSpecification {

    public static Specification<Car> carHasModelName(Integer[] models) {
        return (Specification<Car>) (root, query, cb) -> root.get(Car_.MODEL).in(models);
    }

    public static Specification<Car> carHasSeatNumber(Integer[] number) {
        return (Specification<Car>) (root, query, cb) -> root.get(Car_.SEAT).in(Arrays.asList(number));
    }

    public static Specification<Car> carHasFromPriceTpPrice(double fromPrice, double toPrice) {
        return (Specification<Car>) (root, query, cb) -> cb.between(root.get(Car_.PRICE), fromPrice, toPrice);
    }

    public static Specification<Car> carHasBrand(Integer[] brandId) {
        return (Specification<Car>) (root, query, cb) -> root.get(Car_.BRAND).in(Arrays.asList(brandId));
    }

    public static Specification<Car> carNotOwner(Integer ownerId) {
        return (Specification<Car>) (root, query, cb) -> cb.notEqual(root.get(Car_.OWNER),ownerId);
    }
}
