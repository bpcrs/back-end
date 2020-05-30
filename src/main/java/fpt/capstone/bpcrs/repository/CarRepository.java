package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    Page<Car> findAllByNameContains(String search, Pageable paging);
}
