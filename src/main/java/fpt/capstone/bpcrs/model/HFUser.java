package fpt.capstone.bpcrs.model;

import fpt.capstone.bpcrs.hepler.HFHelper;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.security.PrivateKey;
import java.util.HashSet;
import java.util.Set;

public class HFUser implements User {
    private final String username;
    private UserCertificate userCertificate;
    public HFUser(String certFolder, String username) throws Exception {
        this.username = username;
        userCertificate = HFHelper.loadFromFile(username,certFolder);
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Set<String> getRoles() {
        return new HashSet<String>();
    }

    @Override
    public String getAccount() {
        return "";
    }

    @Override
    public String getAffiliation() {
        return "";
    }

    @Override
    public Enrollment getEnrollment() {
        return new Enrollment() {
            @Override
            public PrivateKey getKey() {
                try {
                    return userCertificate.getCredentials().getPrivateKey();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public String getCert() {
                return userCertificate.getCredentials().getCertificate();
            }
        };
    }

    @Override
    public String getMspId() {
        return userCertificate.getMspId();
    }
}
