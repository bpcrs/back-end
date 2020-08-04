package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.hepler.HFHelper;
import fpt.capstone.bpcrs.payload.DappPayload;
import fpt.capstone.bpcrs.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockchainServiceImpl implements BlockchainService {

    @Autowired
    private HFHelper hfHelper;

    @Override
    public void submitContract() {
        try {
            hfHelper.enrollAdmin();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        DappPayload.ResultChaincode resultChaincode = hfHelper.sendRequest(HFHelper.RequestType.QUERY, "admin",
//                "mychannel", "agreements","queryContract","123");
//        System.out.println(resultChaincode.getData());
//        DappPayload.ResultChaincode resultChaincode = hfHelper.sendRequest(HFHelper.RequestType.INVOKE, "admin",
//                "mychannel", "agreements","createCar","CAR20", "Honda", "Accord", "Red", "HungPT");
//        System.out.println(resultChaincode.getData());
    }

    @Override
    public boolean registerUser(String username) throws Exception {
        return hfHelper.registerUser(username);
    }
}
