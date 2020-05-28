package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
