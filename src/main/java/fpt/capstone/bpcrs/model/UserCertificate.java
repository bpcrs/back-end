package fpt.capstone.bpcrs.model;

import fpt.capstone.bpcrs.hepler.Credentials;

import java.io.Serializable;

public class UserCertificate implements Serializable {
    private String username;
    private int version;
    private String mspId;
    private String type;
    private Credentials credentials;

    public UserCertificate() {
    }

    public UserCertificate(String username, int version, String mspId, String type, Credentials credentials) {
        this.username = username;
        this.version = version;
        this.mspId = mspId;
        this.type = type;
        this.credentials = credentials;
    }

    public UserCertificate(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getVersion() {
        return version;
    }

    public String getMspId() {
        return mspId;
    }

    public String getType() {
        return type;
    }

    public Credentials getCredentials() {
        return credentials;
    }
}

