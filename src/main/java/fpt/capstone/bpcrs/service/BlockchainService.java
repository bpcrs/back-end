package fpt.capstone.bpcrs.service;

import org.springframework.boot.configurationprocessor.json.JSONException;

public interface BlockchainService {
    void submitContract(int bookingId);
    void getContract(int id);
    boolean registerUser(String username) throws JSONException;
}
