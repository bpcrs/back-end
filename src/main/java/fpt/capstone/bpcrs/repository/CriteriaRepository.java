package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria,Integer> {
    Optional<Criteria> findByName(String name);
}
