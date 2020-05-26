package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.payload.AccountRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface AccountService {

  List<Account> getAccounts();

  List<Account> getAdminAccounts();

  Account getAccount(UUID uuid);

  Account getAccountByEmail(String email);

  Account registerAccount(AccountRequest request);

  Account createAccount(AccountRequest request);

  Account updateAccount(UUID uuid, AccountRequest request);

  Account updateProfile(UUID uuid, AccountRequest request) throws IOException;

  Account updateAccountStatus(UUID uuid, Boolean active);

  Account setGoogleAccount(String email, String name, String avatar);

  void confirmAccount(UUID uuid);

}
