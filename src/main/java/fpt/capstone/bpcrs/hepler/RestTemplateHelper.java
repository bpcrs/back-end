package fpt.capstone.bpcrs.hepler;

import fpt.capstone.bpcrs.payload.DappPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateHelper {
    @Value("${dapp.host}")
    private String DAPP_HOST;

    public DappPayload.ResultChaincode httpPost(String endpoint, JSONObject requestBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);
        DappPayload.ResultChaincode resultChaincode = restTemplate.postForObject(DAPP_HOST +
                "/" + endpoint, entity, DappPayload.ResultChaincode.class);
        return resultChaincode;
    }
}
