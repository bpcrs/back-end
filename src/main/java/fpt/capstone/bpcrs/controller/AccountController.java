package fpt.capstone.bpcrs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.webtoken.JsonWebSignature;
import fpt.capstone.bpcrs.component.AuthenticationFacade;
import fpt.capstone.bpcrs.component.GoogleAuthenticator;
import fpt.capstone.bpcrs.component.JwtTokenProvider;
import fpt.capstone.bpcrs.component.UserPrincipal;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.payload.*;
import fpt.capstone.bpcrs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
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
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private GoogleAuthenticator googleAuthenticator;

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

    @GetMapping("/google/authorize")
    public ResponseEntity<?> getAuthorizedURL(
            @RequestParam String redirectUri, @RequestParam String rawId) throws Exception {
        Authentication auth = authenticationFacade.getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        Map<String, Object> state = new HashMap<>();
        state.put("rawId", rawId);
        state.put("accountId", userPrincipal.getId());
        state.put("redirectUri", redirectUri);

        String encodedState =
                Base64.getUrlEncoder().encodeToString(JSON_MAPPER.writeValueAsString(state).getBytes());
        String authorize = googleAuthenticator.authorize(redirectUri, encodedState);

        return ResponseEntity.ok(new ApiResponse<>(true, "Authorized url", authorize));
    }

    @PostMapping("/google/login")
    public ResponseEntity<?> loginByGoogle(@Valid @RequestBody StringWrapperRequest wrapper) {
        try {
            String token = wrapper.getString();
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
                    new ApiResponse<>(true, "Logged successfully", new LoginResponse(jwt)));
        } catch (BadRequestException | GeneralSecurityException | IOException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}
