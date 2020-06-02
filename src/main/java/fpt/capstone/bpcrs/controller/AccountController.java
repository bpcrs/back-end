package fpt.capstone.bpcrs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import fpt.capstone.bpcrs.component.JwtTokenProvider;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.payload.AccountPayload;
import fpt.capstone.bpcrs.payload.AccountResponse;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AccountService accountService;

    @Autowired
    private GoogleIdTokenVerifier googleIdTokenVerifier;

    @GetMapping
    public ResponseEntity<?> getAccounts() {
        List<Account> accounts = accountService.getAccounts();
        List<AccountResponse> response =
                accounts.stream().map(AccountResponse::setResponse).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "", response));
    }

    @PutMapping("/confirm")
    public ResponseEntity<?> confirmAccount(@RequestParam("token") String token) {
        try {
            AccountResponse accountResponse = tokenProvider.getTokenValue(token, AccountResponse.class);
            return ResponseEntity.ok(new ApiResponse<>(true, "", accountResponse));
        } catch (BadRequestException | IOException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAccountStatus(
            @PathVariable("id") int id, @RequestParam("active") boolean active) {
        try {
            Account account = accountService.updateAccountStatus(id, active);
            AccountResponse response = AccountResponse.setResponse(account);
            return ResponseEntity.ok(new ApiResponse<>(true, "Account updated", response));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PostMapping("/google/login")
    public ResponseEntity<?> loginByGoogle(@Valid @RequestBody AccountPayload.GoogleRequestLogin requestLogin) {
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
            String avatar = (String) payload.get("picture");

            Account account = accountService.setGoogleAccount(email, name, avatar);
            AccountResponse response = AccountResponse.setResponse(account);
            String jwt = tokenProvider.generateToken(response);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Logged successfully", new AccountPayload.LoginResponse(jwt)));
        } catch (BadRequestException | GeneralSecurityException | IOException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}
