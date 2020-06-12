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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@Slf4j
public class AccountController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AccountService accountService;

    @Autowired
    private GoogleIdTokenVerifier googleIdTokenVerifier;

    @GetMapping
    public ResponseEntity<?> getAccounts() {
        List<Account> accounts = accountService.getAccounts();
        if(accounts.isEmpty()){
            return new ResponseEntity(new ApiResponse<>(false, "List account is empty"), HttpStatus.BAD_REQUEST);
        }
        List<AccountPayload.AccountResponse>  responses = ObjectMapperUtils.mapAll(accounts, AccountPayload.AccountResponse.class);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get list account successfull", responses));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAccountStatus(
            @PathVariable("id") int id, @RequestParam("active") boolean active) {
        try {
            Account account = accountService.updateAccountStatus(id, active);
            AccountPayload.AccountResponse response = ObjectMapperUtils.map(account, AccountPayload.AccountResponse.class);
            return ResponseEntity.ok(new ApiResponse<>(true, "Account updated", response));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PostMapping("/login")
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
            String imageUrl = (String) payload.get("picture");
            Account account = accountService.setGoogleAccount(email, name, imageUrl);
            AccountPayload.LoginResponse response = ObjectMapperUtils.map(account, AccountPayload.LoginResponse.class);
            String jwt = tokenProvider.generateToken(response);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Logged successfully", new AccountPayload.LoginResponse(jwt)));
        } catch (BadRequestException | GeneralSecurityException | IOException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}
