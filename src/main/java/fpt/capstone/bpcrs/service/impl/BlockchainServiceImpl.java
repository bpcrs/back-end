package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.hepler.HFHelper;
import fpt.capstone.bpcrs.hepler.RestTemplateHelper;
import fpt.capstone.bpcrs.model.Booking;
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
    public boolean submitContract(Booking booking) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("carId", booking.getCar().getId());
        requestBody.put("renterId", booking.getRenter().getId());
        requestBody.put("ownerId", booking.getCar().getId());
        requestBody.put("fromDate", booking.getFromDate());
        requestBody.put("toDate", booking.getToDate());
        requestBody.put("carPrice",booking.getCar().getPrice());
        requestBody.put("totalPrice",booking.getTotalPrice());
        requestBody.put("location",booking.getLocation());
        requestBody.put("destination",booking.getDestination());
        requestBody.put("criteria","[]");
        DappPayload.ResultChaincode resultChaincode = restTemplateHelper.httpPost("submit-contract",requestBody);
        return resultChaincode.isSuccess();
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
