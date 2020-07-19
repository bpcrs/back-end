package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CriteriaRepository extends JpaRepository<Criteria,Integer> {
    Optional<Criteria> findByName(String name);
}
