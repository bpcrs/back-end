package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.constant.CarEnum;
import fpt.capstone.bpcrs.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer>,  JpaSpecificationExecutor<Car> {
    Page<Car> findAllByOwner_Id(int ownerId, Pageable paging);
    Page<Car> findAllByStatus(CarEnum status, Pageable paging);
    Car findByVINOrPlateNum(String VIN,String plateNum);
    List<Car> findCarByOwnerIdAndId(int ownerId, int carId);
}
