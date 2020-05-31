package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.constant.AppConstant;
import fpt.capstone.bpcrs.constant.AuthProvider;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Role;
import fpt.capstone.bpcrs.payload.AccountRequest;
import fpt.capstone.bpcrs.repository.AccountRepository;
import fpt.capstone.bpcrs.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public List<Account> getAccounts() {
    return accountRepository.findAll();
  }

  @Override
  public List<Account> getAdminAccounts() {
    return accountRepository.getByRole_Id(RoleEnum.ADMINISTRATOR.getId());
  }

  @Override
  public Account getAccount(int uuid) {
    return accountRepository.findById(uuid).orElse(null);
  }

  @Override
  public Account getAccountByEmail(String email) {
    return accountRepository.getByEmail(email).orElse(null);
  }

  @Override
  public Account registerAccount(AccountRequest request) {

    if (getAccountByEmail(request.getEmail()) != null) {
      throw new BadRequestException("Email is existed");
    }

    Role role = Role.builder()
        .id(RoleEnum.USER.getId())
        .name(RoleEnum.USER.toString())
        .build();

    return setNewAccount(request, AuthProvider.local, null, role);
  }

  @Override
  public Account createAccount(AccountRequest request) {

    if (getAccountByEmail(request.getEmail()) != null) {
      throw new BadRequestException("Email is existed");
    }

    Role role = Role.builder()
        .id(RoleEnum.ADMINISTRATOR.getId())
        .name(RoleEnum.ADMINISTRATOR.toString())
        .build();

    return setNewAccount(request, AuthProvider.local, null, role);
  }

  @Override
  public Account updateAccount(int uuid, AccountRequest request) {

    Account account = getAccount(uuid);
    if (account == null) {
      throw new BadRequestException("Account doesn't existed");
    }

    account.setFullName(request.getFullName());
    return accountRepository.save(account);
  }

  @Override
  public Account updateProfile(int uuid, AccountRequest request) throws IOException {
    Account account = getAccount(uuid);
    if (account == null || account.getRole().getId().equals(RoleEnum.ADMINISTRATOR.getId())) {
      throw new BadRequestException("Account doesn't existed");
    }

    if (account.getProvider().equals(AuthProvider.google)) {
      throw new BadRequestException("Google account can't be update");
    }

//    if (!StringUtils.isEmpty(request.getImageUrl())) {
//      BufferedImage bufferedImage = ImageUtils.base64ToImage(request.getImageUrl());
//      String avatar = firebaseService.createUserAvatar(bufferedImage, account.getId().toString());
//      account.setImageUrl(avatar);
//    }
    if (!StringUtils.isEmpty(request.getFullName())) {
      account.setFullName(request.getFullName());
    }

    return accountRepository.save(account);
  }

  @Override
  public Account updateAccountStatus(int uuid, Boolean active) {
    Account account = getAccount(uuid);
    if (account == null) {
      throw new BadRequestException("Account doesn't existed");
    }
    account.setActive(active);
    return accountRepository.save(account);
  }

  @Override
  public Account setGoogleAccount(String email, String name, String avatar) {
    Account account = getAccountByEmail(email);
    if (account == null) {

      AccountRequest accountRequest = AccountRequest.builder()
          .email(email)
          .fullName(name)
          .build();

      Role role = Role.builder()
          .id(RoleEnum.USER.getId())
          .name(RoleEnum.USER.toString())
          .build();

      return setNewAccount(accountRequest, AuthProvider.google, avatar, role);

    }

    if (!account.isActive()) {
      throw new BadRequestException("This account was locked");
    }

    if (account.getProvider().equals(AuthProvider.google)) {
      account.setFullName(name);
      account.setImageUrl(avatar);
      return accountRepository.save(account);
    }

    throw new BadRequestException(
        "This email was registered in system. Please use email and password to login");
  }

  @Override
  public void confirmAccount(int uuid) {
    Account account = getAccount(uuid);
    if (account == null) {
      throw new BadRequestException("Account doesn't existed");
    }

    account.setApproved(true);
    accountRepository.save(account);
  }

  private Account setNewAccount(AccountRequest request, AuthProvider provider, String avatarURL,
      Role role) {
    Account account = new Account();
    account.setEmail(request.getEmail());
    account.setFullName(request.getFullName());
    account.setActive(true);
    account.setAllowInvite(false);

    if (role.getId() == RoleEnum.USER.getId()) {

      if (provider.equals(AuthProvider.google)) {
        account.setApproved(true);
      }

      if (!StringUtils.isEmpty(avatarURL)) {
        account.setImageUrl(avatarURL);
      } else {
        account.setImageUrl(AppConstant.DEFAULT_AVATAR_URL);
      }

    } else {
      account.setApproved(true);
      account.setImageUrl(AppConstant.DEFAULT_AVATAR_ADMIN_URL);
    }

    account.setRole(role);
    account.setProvider(provider);
    return accountRepository.save(account);
  }
}
