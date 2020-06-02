package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    Page<Image> findAllByCar_Id(int carIds, Pageable paging);
}
