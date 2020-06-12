package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Role;
import fpt.capstone.bpcrs.repository.AccountRepository;
import fpt.capstone.bpcrs.repository.RoleRepository;
import fpt.capstone.bpcrs.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Account setNewAccount(String email, String fullName, String imageUrl, Role role) {
        Account account = new Account();
        account.setEmail(email);
        account.setFullName(fullName);
        account.setActive(true);
        account.setImageUrl(imageUrl);
        account.setRole(role);
        return accountRepository.save(account);
    }
}
