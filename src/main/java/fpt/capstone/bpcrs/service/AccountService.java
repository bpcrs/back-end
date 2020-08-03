package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.payload.AccountPayload;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

  List<Account> getAccounts();

  Account getAccountByEmail(String email);

  Account updateAccountStatus(int id, Boolean active);

  Account setGoogleAccount(String email, String name, String imageUrl);

  Account getCurrentUser();

  Account getAccountById(int id);

  Account updateAccountLicense(Account accountUpdate, int id);

  Account updateAccount(int id, String phone);


}
