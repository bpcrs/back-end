package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.constant.ImageTypeEnum;
import fpt.capstone.bpcrs.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    Page<Image> findAllByCar_IdAndType(Integer car_id, ImageTypeEnum type, Pageable pageable);
    List<Image> findAllByCar_IdAndType(Integer car_id, ImageTypeEnum type);
}
