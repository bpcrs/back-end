package fpt.capstone.bpcrs.repository;

import fpt.capstone.bpcrs.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

  Optional<Account> getByEmail(String email);

  List<Account> getAllByRole_Name(String name);

}
