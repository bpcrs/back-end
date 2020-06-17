package fpt.capstone.bpcrs.component;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.GmailScopes;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GoogleAuthenticator {

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  @Value("${spring.security.oauth2.client.registration.google.clientId}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.google.clientSecret}")
  private String clientSecret;

  public GoogleAuthorizationCodeFlow getFlow() throws GeneralSecurityException, IOException {
    Details web = new Details();
    web.setClientId(clientId);
    web.setClientSecret(clientSecret);
    GoogleClientSecrets clientSecrets = new GoogleClientSecrets().setWeb(web);
    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    return new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets,
            Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM))
        .setAccessType("online")
        .setApprovalPrompt("force")
        .build();
  }
}
