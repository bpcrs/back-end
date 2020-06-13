package fpt.capstone.bpcrs.service;

import generated.QueryProto;
import generated.QueryServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DappService {
    @Value("${dapp.host}")
    private String dappHost;
    @Value("${dapp.port}")
    private int dappPort;

    public void getDapp(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(dappHost,dappPort).usePlaintext().build();
        QueryServiceGrpc.QueryServiceBlockingStub stub = QueryServiceGrpc.newBlockingStub(channel);

        QueryProto.QueryResponse response = stub.sendQuery(QueryProto.QueryRequest.newBuilder().setChaincode("fabcar")
                .setUsername("admin").setChannel("mychannel").setFuncName("queryAllCars").build());
        System.out.println(response.getMessage());
        System.out.println(response.getData());
        channel.shutdown();
    }
}
