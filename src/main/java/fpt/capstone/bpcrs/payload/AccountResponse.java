package fpt.capstone.bpcrs.payload;

import fpt.capstone.bpcrs.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

  private UUID id;
  private String fullName;
  private String email;
  private String imageUrl;
  private boolean active;
  private boolean allowInvite;
  private String provider;
  private int roleId;
  private String roleName;

  public static AccountResponse setResponse(Account account) {
    return AccountResponse.builder()
        .id(account.getId())
        .fullName(account.getFullName())
        .email(account.getEmail())
        .imageUrl(account.getImageUrl())
        .provider(account.getProvider().name())
        .active(account.isActive())
        .allowInvite(account.isAllowInvite())
        .roleId(account.getRole().getId())
        .roleName(account.getRole().getName())
        .build();
  }
}
