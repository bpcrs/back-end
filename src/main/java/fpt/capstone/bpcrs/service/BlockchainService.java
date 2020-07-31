package fpt.capstone.bpcrs.service;

public interface BlockchainService {
    void submitContract();
    boolean registerUser(String username) throws Exception;
}
