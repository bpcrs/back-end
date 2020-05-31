package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.UserPrincipal;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.repository.AccountRepository;
import fpt.capstone.bpcrs.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService, UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account = accountRepository.getByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
    return UserPrincipal.create(account);
  }

  @Override
  public UserDetails loadUserFromID(int id) {
    Account account = accountRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User not found\""));
    return UserPrincipal.create(account);
  }
}
