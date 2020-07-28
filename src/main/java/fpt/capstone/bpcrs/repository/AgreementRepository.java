package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Agreement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Integer> {

    List<Agreement> findAllByBooking_IdAndCriteria_IsRenter(int bookingId, boolean isRenter);

    Page<Agreement> findAllByCriteria_Id(int criteriaId, Pageable paging);

    Page<Agreement> findAll(Pageable paging);

    Agreement findByCriteria_IdAndBooking_Id(int criteriaId, int bookingId);

}
