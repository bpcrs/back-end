package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Account;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

  Optional<Account> getByEmail(String email);

  List<Account> getAllByRole_Name(String name);

}
