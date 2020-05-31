package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Role;
import fpt.capstone.bpcrs.payload.AccountRequest;
import fpt.capstone.bpcrs.repository.AccountRepository;
import fpt.capstone.bpcrs.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

  @Autowired private AccountRepository accountRepository;

  @Override
  public List<Account> getAccounts() {
    return accountRepository.findAll();
  }

  @Override
  public Account getAccountByEmail(String email) {
    return accountRepository.getByEmail(email).orElse(null);
  }

  @Override
  public Account updateAccountStatus(int id, Boolean active) {
    if (accountRepository.findById(id).isPresent()) {
      throw new BadRequestException("Account doesn't existed");
    }
    Account account = accountRepository.findById(id).get();

    account.setActive(active);
    return accountRepository.save(account);
  }

  @Override
  public Account setGoogleAccount(String email, String name, String avatar) {
    Account account = getAccountByEmail(email);
    if (account == null) {
      AccountRequest accountRequest = AccountRequest.builder().email(email).fullName(name).build();
      Role role = Role.builder().id(RoleEnum.USER.getId()).name(RoleEnum.USER.toString()).build();
      return setNewAccount(accountRequest, avatar, role);
    }
    if (!account.isActive()) {
      throw new BadRequestException("This account was locked");
    }
    account.setFullName(name);
    account.setImageUrl(avatar);
    return accountRepository.save(account);
  }

  private Account setNewAccount(AccountRequest request, String avatarURL, Role role) {
    Account account = new Account();
    account.setEmail(request.getEmail());
    account.setFullName(request.getFullName());
    account.setActive(true);
    account.setImageUrl(avatarURL);
    account.setRole(role);
    return accountRepository.save(account);
  }
}
