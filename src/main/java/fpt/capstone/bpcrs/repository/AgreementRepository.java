package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Agreement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Integer> {

    //Page<Agreement> findAllByBooking_id(int id, Pageable paging);

    Page<Agreement> findAllByCriteria_Id(int id, Pageable paging);

    Page<Agreement> findAll(Pageable paging);

}
