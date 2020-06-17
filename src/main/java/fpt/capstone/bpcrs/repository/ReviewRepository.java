package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Page<Review> findAllByCar_Id(int carIds, Pageable paging);

}
