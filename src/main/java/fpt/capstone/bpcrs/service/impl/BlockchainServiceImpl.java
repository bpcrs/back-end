package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.hepler.HFHelper;
import fpt.capstone.bpcrs.hepler.RestTemplateHelper;
import fpt.capstone.bpcrs.payload.DappPayload;
import fpt.capstone.bpcrs.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BlockchainServiceImpl implements BlockchainService {


    @Autowired
    private RestTemplateHelper restTemplateHelper;

    @Override
    public void submitContract(int bookingId) {
//        try {
//            hfHelper.enrollAdmin();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        DappPayload.ResultChaincode resultChaincode = hfHelper.sendRequest(HFHelper.RequestType.INVOKE, "admin",
//                "mychannel", "agreements","submitContract",
//                "1","1","1","2",new Date().toString(),new Date().toString(),"HCM","VT","1000","5000","[]");
//        System.out.println(resultChaincode.getData());
    }

    @Override
    public void getContract(int id) {
//        DappPayload.ResultChaincode resultChaincode = hfHelper.sendRequest(HFHelper.RequestType.QUERY, "admin",
//                "mychannel", "agreements","queryContract",id + "");
//        System.out.println(resultChaincode.getData());
    }

    @Override
    public boolean registerUser(String username) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", username);
        DappPayload.ResultChaincode resultChaincode = restTemplateHelper.httpPost("register",requestBody);
        return resultChaincode.isSuccess();
    }
}
