package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    Page<Car> findAllByNameContainsAndIsAvailableIsTrue(String search, Pageable paging);

    int countAllByIsAvailableIsTrue();
}
