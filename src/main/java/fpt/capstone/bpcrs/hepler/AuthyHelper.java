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


}
