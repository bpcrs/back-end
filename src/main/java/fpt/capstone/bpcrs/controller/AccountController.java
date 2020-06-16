package fpt.capstone.bpcrs.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import fpt.capstone.bpcrs.component.JwtTokenProvider;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.payload.AccountPayload;
import fpt.capstone.bpcrs.payload.AccountPayload.AccountResponse;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.service.AccountService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

  @Autowired
  private JwtTokenProvider tokenProvider;

  @Autowired
  private AccountService accountService;

  @Autowired
  private GoogleIdTokenVerifier googleIdTokenVerifier;

  @GetMapping
  @PreAuthorize("hasRole('ADMINISTRATOR')")
  public ResponseEntity<?> getAccounts() {
    List<Account> accounts = accountService.getAccounts();
    if (accounts.isEmpty()) {
      return new ResponseEntity(new ApiResponse<>(false, "List account is empty"),
          HttpStatus.BAD_REQUEST);
    }
    List<AccountPayload.AccountResponse> responses = ObjectMapperUtils
        .mapAll(accounts, AccountPayload.AccountResponse.class);
    for (int i = 0; i < responses.size(); i++) {
      responses.get(i).setRole(accounts.get(i).getRole().getName());
    }
    return ResponseEntity.ok(new ApiResponse<>(true, "Get list account successfull", responses));
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ADMINISTRATOR')")
  public ResponseEntity<?> updateAccountStatus(
      @PathVariable("id") int id, @RequestParam("active") boolean active) {
    try {
      Account account = accountService.updateAccountStatus(id, active);
      AccountPayload.AccountResponse response = ObjectMapperUtils
          .map(account, AccountPayload.AccountResponse.class);
      return ResponseEntity.ok(new ApiResponse<>(true, "Account updated", response));
    } catch (BadRequestException ex) {
      return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
    }
  }

  @PostMapping("/google/login")
  public ResponseEntity<?> loginByGoogle(
      @Valid @RequestBody AccountPayload.GoogleRequestLogin requestLogin) {
    try {
      String token = requestLogin.getToken();
      if (StringUtils.isEmpty(token)) {
        throw new BadCredentialsException("Invalid login information");
      }
      GoogleIdToken googleIdToken = googleIdTokenVerifier.verify(token);
      if (googleIdToken == null) {
        throw new BadRequestException("Google token error");
      }
      GoogleIdToken.Payload payload = googleIdToken.getPayload();
      String email = payload.getEmail();
      String name = (String) payload.get("name");
      String imageUrl = (String) payload.get("picture");
      Account account = accountService.getAccountByEmail(email);
      if (account == null) {
        account = accountService.setGoogleAccount(email, name, imageUrl);
      }
      String jwt = tokenProvider
          .generateToken(AccountResponse.builder().role(account.getRole().getName())
              .email(account.getEmail())
              .fullName(account.getFullName())
              .imageUrl(account.getImageUrl())
              .build());
      return ResponseEntity.ok(
          new ApiResponse<>(true, "Logged successfully", new AccountPayload.LoginResponse(jwt)));
    } catch (BadRequestException | GeneralSecurityException | IOException ex) {
      return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
    }
  }
}
