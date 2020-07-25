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

    Page<Image> findAllByCar_IdAndType(int carIds, ImageTypeEnum type, Pageable paging);
    List<Image> findAllByCar_IdAndType(int carId, ImageTypeEnum type);
}
