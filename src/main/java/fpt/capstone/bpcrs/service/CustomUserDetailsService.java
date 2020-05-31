package fpt.capstone.bpcrs.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface CustomUserDetailsService {

  UserDetails loadUserFromID(int id);
}
