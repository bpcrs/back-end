package fpt.capstone.bpcrs.component;

import fpt.capstone.bpcrs.model.Account;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
public class UserPrincipal implements UserDetails {

  private UUID id;
  private String fullName;
  private String email;
  private boolean active;
  private boolean approved;
  private String imageUrl;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;

  public UserPrincipal(
      UUID id,
      String fullName,
      String email,
      Boolean active,
      Boolean approved,
      String imageUrl,
      List<GrantedAuthority> authorities) {
    this.id = id;
    this.fullName = fullName;
    this.email = email;
    this.active = active;
    this.approved = approved;
    this.authorities = authorities;
    this.imageUrl = imageUrl;
  }

  public static UserPrincipal create(Account account) {
    List<GrantedAuthority> grantedAuthorities =
        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + account.getRole().getName());
    return new UserPrincipal(
        account.getId(),
        account.getFullName(),
        account.getEmail(),
        account.isActive(),
        account.isApproved(),
        account.getImageUrl(),
        grantedAuthorities);
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.active && this.approved;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserPrincipal that = (UserPrincipal) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }
}
