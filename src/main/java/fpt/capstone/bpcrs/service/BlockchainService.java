package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Booking;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface BlockchainService {
    boolean submitContract(Booking booking) throws JSONException;
    void getContract(int id);
    boolean registerUser(String username) throws JSONException;
    boolean signingContract(Booking booking, boolean isOwner) throws JSONException;
}
