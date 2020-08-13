package fpt.capstone.bpcrs.hepler;

import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.authy.*;
import com.authy.api.*;

@Component
public class AuthyHelper {
    @Value("${authy.apiKey}")
    private String apiKey;

    public int registerUserToAuthy(String email, String phone) throws AuthyException {
        AuthyApiClient client = new AuthyApiClient(apiKey);
        User user = client.getUsers().createUser(email, phone, "84");

        if (user.isOk()){
            return user.getId();
        } else {
            throw new AuthyException(user.getError().getMessage());
        }
    }

    public boolean sendOTPAuthy(int authyId) throws AuthyException {
        AuthyApiClient client = new AuthyApiClient(apiKey);
        Hash response = client.getUsers().requestSms(authyId);
        if (response.isSuccess()){
            return true;
        } else {
            throw new AuthyException(response.getError().getMessage());
        }
    }

    public boolean confirmOTP(int authyId, String otp) throws AuthyException {
        AuthyApiClient client = new AuthyApiClient(apiKey);
        boolean isComfirmed = client.getUsers().requestStatus(authyId).isConfirmed();
        if (!isComfirmed){
            throw new AuthyException("Your phone not confirmed");
        }
        Tokens tokens = client.getTokens();
        Token response = tokens.verify(authyId, otp);
        if (response.isOk()){
            return true;
        } else {
            throw new AuthyException("Invalid OTP");
        }
    }

    public boolean verifyAccount(int authyId) throws AuthyException {
        AuthyApiClient client = new AuthyApiClient(apiKey);
        return client.getUsers().requestStatus(authyId).isConfirmed();
    }
}
