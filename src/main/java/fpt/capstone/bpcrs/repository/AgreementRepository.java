package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Agreement;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Integer> {

    List<Agreement> findAllByBooking_id(int bookingId);

    Page<Agreement> findAllByCriteria_Id(int criteriaId, Pageable paging);

    Page<Agreement> findAll(Pageable paging);

}
