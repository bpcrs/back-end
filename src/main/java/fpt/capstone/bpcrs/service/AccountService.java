package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.payload.AccountRequest;

import java.util.List;
import java.util.UUID;

public interface AccountService {

  List<Account> getAccounts();

  Account getAccountByEmail(String email);

  Account updateAccountStatus(int id, Boolean active);

  Account setGoogleAccount(String email, String name, String avatar);

}
