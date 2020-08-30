package fpt.capstone.bpcrs.service;

import com.authy.AuthyException;
import fpt.capstone.bpcrs.model.Account;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

    Page<Account> getAllAccountsByAdmin(int page, int size);

    List<Account> getAccountsForLicenseCheck();

    Account updateAccountStatusByAdmin(int id);

    Account getAccountByEmail(String email);

    Account updateAccountStatus(int id, Boolean active);

    Account setGoogleAccount(String email, String name, String imageUrl);

    Account getCurrentUser();

    Account getAccountById(int id);

    Account updateAccountLicense(Account accountUpdate, int id);

    Account updateAccount(Account account, String phone) throws AuthyException;

    boolean sendOTP(Account account) throws AuthyException;

    boolean confirmOTP(int authyId, String otp) throws AuthyException;

    boolean verifyAccounnt(int authyId) throws AuthyException;
}
