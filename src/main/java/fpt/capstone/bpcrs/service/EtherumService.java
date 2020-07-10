package fpt.capstone.bpcrs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class EtherumService {
    public void test() throws IOException {
        Web3j web3 = Web3j.build(new HttpService("http://localhost:8545"));
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        EthBlockNumber we3BlockNumber = web3.ethBlockNumber().send();

        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("Client: " + clientVersion);
        System.out.println("Blocknum: " + we3BlockNumber.getBlockNumber());
    }
}
