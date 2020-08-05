package fpt.capstone.bpcrs.hepler;

import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.capstone.bpcrs.model.UserCertificate;
import fpt.capstone.bpcrs.payload.DappPayload;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

@Component
public class HFHelper {
    @Value("${dapp.pem.file}")
    private String PEM_FILE_PATH;
    @Value("${dapp.ca.url}")
    private String CA_URL;
    private static final String ADMIN_USERNAME = "admin";
    @Value("${dapp.network.config.path}")
    private String NETWORK_PATH;


    public enum RequestType {
        INVOKE,
        QUERY
    }

    // Create a CA client for interacting with the CA.
//    public HFCAClient getCAClient() throws Exception {
//        Properties props = new Properties();
//        props.put("pemFile", "crypto-config/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem");
//        props.put("allowAllHostNames", true);
//        HFCAClient hfcaClient = HFCAClient.createNewInstance("https://localhost:7054", props);
//        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
//        hfcaClient.setCryptoSuite(cryptoSuite);
//        return hfcaClient;
//    }
//
//    public void enrollAdmin() throws Exception {
//        HFCAClient caClient = getCAClient();
//        // Create a wallet for managing identities
//        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));
//
//        // Check to see if we've already enrolled the admin user.
//        boolean adminExists = wallet.get("admin") != null;
//        if (adminExists) {
//            System.out.println("An identity for the admin user \"admin\" already exists in the wallet");
//            return;
//        }
//
//        // Enroll the admin user, and import the new identity into the wallet.
//        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
//        enrollmentRequestTLS.addHost("localhost");
//        enrollmentRequestTLS.setProfile("tls");
//        Enrollment enrollment = caClient.enroll("admin", "adminpw", enrollmentRequestTLS);
//        X509Identity user = Identities.newX509Identity("Org1MSP", enrollment);
//        wallet.put("admin", user);
//        System.out.println("Successfully enrolled user \"admin\" and imported it into the wallet");
//    }
//
//    public boolean registerUser(String username) throws Exception {
//        HFCAClient caClient = getCAClient();
//        // Create a wallet for managing identities
//        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));
//
//        // Check to see if we've already enrolled the user.
//        boolean userExists = wallet.get(username) != null;
//        if (userExists) {
//            System.out.println("An identity for the user \"" + username + "\" already exists in the wallet");
//            return false;
//        }
//
//        userExists = wallet.get(ADMIN_USERNAME) != null;
//        if (!userExists) {
//            System.out.println("\"" + ADMIN_USERNAME + "\" needs to be enrolled and added to the wallet first");
//            return false;
//        }
//
//        HFUser adminUser = new HFUser("wallet", ADMIN_USERNAME);
//
//        // Register the user, enroll the user, and import the new identity into the wallet.
//        RegistrationRequest registrationRequest = new RegistrationRequest(username);
//        registrationRequest.setAffiliation("org1.department1");
//        registrationRequest.setEnrollmentID(username);
//
//        String enrollmentSecret = caClient.register(registrationRequest, adminUser);
//        Enrollment enrollment = caClient.enroll(username, enrollmentSecret);
//        Identity user = Identities.newX509Identity("Org1MSP", enrollment);
//        wallet.put(username, user);
//        System.out.println("Successfully registered user \"" + username + "\" and imported it into the wallet");
//        return true;
//    }

//    public static UserCertificate loadFromFile(String username, String certFolder) throws Exception {
//        Path path = Paths.get(certFolder, username + ".id");
//        if (!path.toFile().exists()) return null;
//        File file = new File(path.toString());
//        return new ObjectMapper().readValue(file, UserCertificate.class);
//    }
//
//    public  DappPayload.ResultChaincode sendRequest(RequestType type, String username, String channel,
//                                                 String chaincodeId, String funcName,
//                                                 String... args) {
//        // Load a file system based wallet for managing identities.
//        DappPayload.ResultChaincode resultChaincode = new DappPayload.ResultChaincode();
//        Path walletPath = Paths.get("wallet");
//        Wallet wallet = null;
//        try {
//            wallet = Wallets.newFileSystemWallet(walletPath);
//        } catch (IOException e) {
//            resultChaincode.setMessage("[wallet] folder notfound in system");
//        }
//        // load a CCP
//        Path networkConfigPath = Paths.get(NETWORK_PATH);
//
//        Gateway.Builder builder = Gateway.createBuilder();
//
//        try {
//            wallet.get(username);
//        } catch (IOException e) {
//            resultChaincode.setMessage("Wallet [" + username +"] not found");
//        }
//
//        try {
//            builder.identity(wallet, username).networkConfig(networkConfigPath).discovery(true);
//        } catch (IOException e) {
//            resultChaincode.setMessage("Network Configuration not found");
//        }
//        try (Gateway gateway = builder.connect()) {
//
//            // get the network and contract
//            Network network = gateway.getNetwork(channel);
//            if (network == null) {
//                resultChaincode.setMessage("Channel [" + channel + "] not found");
//            }
//            Contract contract = network.getContract(chaincodeId);
//
//            byte[] result;
//            if (type == RequestType.INVOKE) {
//                result = contract.createTransaction(funcName).submit(args);
//                resultChaincode.setMessage("Invoke Successful");
//                resultChaincode.setData(new String(result));
//            } else if (type == RequestType.QUERY) {
//                result = contract.evaluateTransaction(funcName, args);
//                resultChaincode.setData(new String(result));
//                resultChaincode.setMessage("Query Successful");
//            }
//        } catch (Exception e) {
//            resultChaincode.setMessage(e.getMessage());
//        }
//        return resultChaincode;
//    }
}
