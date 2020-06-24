package fpt.capstone.bpcrs.model.specification;

import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Car_;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecification  {

   public static Specification<Car> carHasModelName(String model) {
      return (Specification<Car>) (root, query, cb) -> cb.equal(root.get(Car_.MODEL), model);
   }

   public static Specification<Car> carHasSeatNumber(int number) {
      return (Specification<Car>) (root, query, cb) -> cb.equal(root.get(Car_.SEAT), number);
   }

   public static Specification<Car> carHasFromPriceTpPrice(double fromPrice, double toPrice ) {
      return (Specification<Car>) (root, query, cb) -> cb.between(root.get(Car_.PRICE), fromPrice, toPrice);
   }

   public static Specification<Car> carHasBrand(int brandId) {
      return (Specification<Car>) (root, query, cb) -> cb.equal(root.get(Car_.BRAND), brandId);
   }
}
