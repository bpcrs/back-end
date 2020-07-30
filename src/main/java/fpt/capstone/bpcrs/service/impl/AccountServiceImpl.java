package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.IgnoreNullProperty;
import fpt.capstone.bpcrs.component.UserPrincipal;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Role;
import fpt.capstone.bpcrs.payload.AccountPayload;
import fpt.capstone.bpcrs.repository.AccountRepository;
import fpt.capstone.bpcrs.repository.RoleRepository;
import fpt.capstone.bpcrs.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Account> getAccounts() {
        Role role = roleRepository.findByName(RoleEnum.USER.toString());
        return accountRepository.getAllByRole_Id(role.getId());
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.getByEmail(email).orElse(null);
    }

    @Override
    public Account updateAccountStatus(int id, Boolean active) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            throw new BadRequestException("Account doesn't existed");
        }
        account.setActive(active);
        return accountRepository.save(account);
    }

    @Override
    public Account setGoogleAccount(String email, String name, String imageUrl) {
        Account account = getAccountByEmail(email);
        if (account == null) {
            Role role = roleRepository.findByName(RoleEnum.USER.name());
            return setNewAccount(email, name, imageUrl, role);
        }
        if (!account.isActive()) {
            throw new BadRequestException("This account was locked");
        }
        account.setFullName(name);
        account.setImageUrl(imageUrl);
        return accountRepository.save(account);
    }

    @Override
    public Account getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        return getAccountByEmail(userPrincipal.getEmail());
    }

    @Override
    public Account getAccountById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account updateAccount(int id, String phone) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            throw new BadRequestException("Account doesn't existed");
        }
//        account.setPhoneNumber(phone);
        account.setPhone(phone);
        return account;
    }

    private Account setNewAccount(String email, String fullName, String imageUrl, Role role) {
        Account account = new Account();
        account.setEmail(email);
        account.setFullName(fullName);
        account.setActive(true);
        account.setImageUrl(imageUrl);
        account.setRole(role);
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccountLicense(Account accountUpdate, int id) {
        Account account = accountRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(accountUpdate, account, IgnoreNullProperty.getNullPropertyNames(accountUpdate));
        return accountRepository.save(account);
    }


}
