package fpt.capstone.bpcrs.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {

  UserDetails loadUserFromID(int id);
}
