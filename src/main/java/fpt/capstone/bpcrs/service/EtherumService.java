package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.contracts.Adoption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class EtherumService {
    public void test() throws Exception {
        Web3j web3 = Web3j.build(new HttpService("http://localhost:8545"));
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        EthBlockNumber we3BlockNumber = web3.ethBlockNumber().send();
        Credentials creds = Credentials.create("c87509a1c067bbde78beb793e6fa76530b6382a4c0241e5e4a9ec0a0f44dc0d3");
        Adoption adoption = Adoption.load(creds.getAddress(),web3,creds,new DefaultGasProvider());
        TransactionReceipt  receipt = adoption.adopt(BigInteger.valueOf(1)).sendAsync().get();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("Client: " + clientVersion);
        System.out.println("TX: " + receipt.getTransactionHash());
        System.out.println("Blocknum: " + we3BlockNumber.getBlockNumber());
    }
}
