package fpt.capstone.bpcrs.service;

import com.authy.AuthyException;
import fpt.capstone.bpcrs.model.Account;

import java.util.List;

public interface AccountService {

    List<Account> getAccounts();

    Account getAccountByEmail(String email);

    Account updateAccountStatus(int id, Boolean active);

    Account setGoogleAccount(String email, String name, String imageUrl);

    Account getCurrentUser();

    Account getAccountById(int id);

    Account updateAccountLicense(Account accountUpdate, int id);

    Account updateAccount(Account account, String phone) throws AuthyException;

    boolean sendOTP(Account account) throws AuthyException;

    boolean confirmOTP(int authyId, String otp) throws AuthyException;
}
