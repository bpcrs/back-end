package fpt.capstone.bpcrs.controller;

import com.authy.AuthyException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import fpt.capstone.bpcrs.component.JwtTokenProvider;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.payload.AccountPayload;
import fpt.capstone.bpcrs.payload.AccountPayload.AccountResponse;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.service.AccountService;
import fpt.capstone.bpcrs.service.BlockchainService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

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

    @Autowired
    private BlockchainService blockchainService;


    @GetMapping
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> getAccounts() {
        List<Account> accounts = accountService.getAccounts();
        if (accounts.isEmpty()) {
            return new ResponseEntity(new ApiResponse<>(false, "List account is empty"),
                    HttpStatus.BAD_REQUEST);
        }
        List<AccountPayload.AccountResponseChecking> responses = ObjectMapperUtils
                .mapAll(accounts, AccountPayload.AccountResponseChecking.class);
//        for (int i = 0; i < responses.size(); i++) {
//            responses.get(i).setRole(accounts.get(i).getRole().getName());
//        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Get list account successful", responses));
    }

    @PatchMapping("/{id}")
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
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
            } else if (!account.isActive()) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Your account has been disabled.",
                        null));
            }
            boolean isSuccess = blockchainService.registerUser(email);
            if (!isSuccess) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Can't register user on blockchain",
                        null));
            }
            String jwt = tokenProvider
                    .generateToken(AccountResponse.builder()
                            .id(account.getId())
                            .role(account.getRole().getName())
                            .email(account.getEmail())
                            .fullName(account.getFullName())
                            .imageUrl(account.getImageUrl())
                            .phone(account.getPhone())
                            .build());
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Logged successfully", jwt));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable int id) {
        AccountPayload.AccountResponse response = new AccountPayload.AccountResponse();
        Account account = accountService.getAccountById(id);
        if (account == null) {
            return new ResponseEntity<>(new ApiError("Account with id= " + id + "not found", " "),
                    HttpStatus.BAD_REQUEST);
        }
        account.modelMaplerToObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    @PutMapping("/license")
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> updateAccountLicense(
            @RequestBody AccountPayload.AccountRequestUpdate request) {
        try {
            Account account = accountService.getCurrentUser();
            account.setActive(true);
            account.setImageLicense(request.getImageLicense());
            account.setIdentification(request.getIdentification());
            account = accountService.updateAccount(account, request.getPhone());
            AccountPayload.AccountRequestUpdate response = ObjectMapperUtils.map(account,
                    AccountPayload.AccountRequestUpdate.class);
            return ResponseEntity.ok(new ApiResponse<>(true, response));

        } catch (BadRequestException | AuthyException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PostMapping("/send-otp")
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> sendRequestOTP() {
        try {
            Account account = accountService.getCurrentUser();
            boolean isSuccess = accountService.sendOTP(account);
            return ResponseEntity.ok(new ApiResponse<>(isSuccess, "Sent OTP to " + account.getPhone() + " or Authy " +
                    "App"));
        } catch (BadRequestException | AuthyException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PostMapping("/confirm-otp")
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> confrimOTP(@RequestParam String otp) {
        try {
            Account account = accountService.getCurrentUser();
            boolean isSuccess = accountService.confirmOTP(account.getAuthyId(), otp);
            return ResponseEntity.ok(new ApiResponse<>(isSuccess, "OTP is valid"));
        } catch (BadRequestException | AuthyException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PostMapping("/verify")
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> checkVerifiedAccount(){
        try {
            Account account = accountService.getCurrentUser();
            if (account.getAuthyId() == null){
                return ResponseEntity.ok(new ApiResponse<>(false, "Unverified"));
            }
            boolean isSuccess = accountService.verifyAccounnt(account.getAuthyId());
            return ResponseEntity.ok(new ApiResponse<>(isSuccess, isSuccess ? "Verified" : "Unverified"));
        } catch (BadRequestException | AuthyException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
//
//    @PutMapping("license/{id}")
//    @RolesAllowed(RoleEnum.RoleType.USER)
//    public ResponseEntity<?> updateAccount(@RequestParam String phone) {
//        try {
////            String patterns = "^\\d{10}$" + "^\\d{11}$";
////            Pattern pattern = Pattern.compile(patterns);
////            Matcher matcher = pattern.matcher(phone);
////            if (!matcher.matches()) {
////                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid phone number", null));
////            }
//            Account account = accountService.getCurrentUser();
//            account = accountService.updateAccount(account, phone);
//            AccountPayload.AccountResponse response = ObjectMapperUtils
//                    .map(account, AccountPayload.AccountResponse.class);
//            return ResponseEntity.ok(new ApiResponse<>(true, "Account updated", response));
//        } catch (BadRequestException | AuthyException ex) {
//            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
//        }
//    }
}
