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

        DappPayload.ResultChaincode resultChaincode = hfHelper.sendRequest(HFHelper.RequestType.QUERY, "admin",
                "mychannel", "agreements","queryAllCars","");
        System.out.println(resultChaincode.getData());
//        DappPayload.ResultChaincode resultChaincode = hfHelper.sendRequest(HFHelper.RequestType.INVOKE, "admin",
//                "mychannel", "agreements","createCar","CAR20", "Honda", "Accord", "Red", "HungPT");
//        System.out.println(resultChaincode.getData());
    }
}
