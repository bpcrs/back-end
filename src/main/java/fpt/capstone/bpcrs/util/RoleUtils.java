package fpt.capstone.bpcrs.util;

import fpt.capstone.bpcrs.constant.RoleEnum;
import org.springframework.security.core.Authentication;

public class RoleUtils {

  public static boolean hasAdminRole(Authentication auth) {
    if (auth == null) {
      return false;
    }
    return auth.getAuthorities().stream()
        .anyMatch(r -> r.getAuthority().contains(RoleEnum.ADMINISTRATOR.getName()));
  }
}
