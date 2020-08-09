package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.hepler.RestTemplateHelper;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.payload.DappPayload;
import fpt.capstone.bpcrs.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class BlockchainServiceImpl implements BlockchainService {


    @Autowired
    private RestTemplateHelper restTemplateHelper;

    @Override
    public boolean submitContract(Booking booking) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("bookingId", booking.getId());
        requestBody.put("carId", booking.getCar().getId());
        requestBody.put("renter", booking.getRenter().getEmail());
        requestBody.put("owner", booking.getCar().getOwner().getEmail());
        requestBody.put("fromDate", booking.getFromDate());
        requestBody.put("toDate", booking.getToDate());
        requestBody.put("carPrice", booking.getCar().getPrice());
        requestBody.put("totalPrice", booking.getTotalPrice());
        requestBody.put("location", booking.getLocation());
        requestBody.put("destination", booking.getDestination());
        requestBody.put("criteria", booking.agreementsToJSONArray());
        DappPayload.ResultChaincode resultChaincode = restTemplateHelper.httpPost("submit-contract", requestBody);
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
        DappPayload.ResultChaincode resultChaincode = restTemplateHelper.httpPost("register", requestBody);
        return resultChaincode.isSuccess();
    }

    @Override
    public DappPayload.ResultChaincode signingContract(Booking booking, boolean isOwner) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("data", booking.toString());
        requestBody.put("bookingId", booking.getId());
        requestBody.put("carId", booking.getCar().getId());
        requestBody.put("renter", booking.getRenter().getEmail());
        requestBody.put("owner", booking.getCar().getOwner().getEmail());
        requestBody.put("isOwner", isOwner);
        DappPayload.ResultChaincode resultChaincode = restTemplateHelper.httpPost("sign-contract", requestBody);
        return resultChaincode;
    }
}
