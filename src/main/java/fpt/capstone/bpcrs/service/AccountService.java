package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.payload.AccountRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface AccountService {

  List<Account> getAccounts();

  List<Account> getAdminAccounts();

  Account getAccount(int uuid);

  Account getAccountByEmail(String email);

  Account registerAccount(AccountRequest request);

  Account createAccount(AccountRequest request);

  Account updateAccount(int uuid, AccountRequest request);

  Account updateProfile(int uuid, AccountRequest request) throws IOException;

  Account updateAccountStatus(int uuid, Boolean active);

  Account setGoogleAccount(String email, String name, String avatar);

  void confirmAccount(int uuid);

}
