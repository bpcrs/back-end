package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.payload.AccountPayload;

import java.util.List;

public interface AccountService {

  List<Account> getAccounts();

  Account getAccountByEmail(String email);

  Account updateAccountStatus(int id, Boolean active);

  Account setGoogleAccount(String email, String name, String imageUrl);

  Account getCurrentUser();

  Account updateAccountAddress(int id, AccountPayload.AccountAddressUpdate request);
}
