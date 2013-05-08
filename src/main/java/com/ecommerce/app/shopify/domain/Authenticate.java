package com.ecommerce.app.shopify.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Authenticate implements java.io.Serializable {

    private long authId;
    private long profileId;
    private String uname;
    private String pwd;
    private String verificationCode;
    private Date changeTime;
    private Boolean accLock;

    public Authenticate() {
    }

    public Authenticate(ResultSet rs) throws SQLException {
        this.authId = rs.getInt("AUTH_ID");
        this.profileId = rs.getInt("PROFILE_ID");
        this.uname = rs.getString("UNAME");
        this.pwd = rs.getString("PWD");
        this.verificationCode = rs.getString("VERIFICATION_CODE");
        this.changeTime = rs.getTimestamp("CHANGE_TIME");
        this.accLock = rs.getBoolean("ACC_LOCK");
        
    }

    public Authenticate(long authId, long profileId, String uname, String pwd, String verificationCode, Date changeTime,Boolean accLock) {
        this.authId = authId;
        this.profileId = profileId;
        this.uname = uname;
        this.pwd = pwd;
        this.verificationCode = verificationCode;
        this.changeTime = changeTime;
        this.accLock = accLock;
        
    }

    public long getAuthId() {
        return this.authId;
    }

    public void setAuthId(long authId) {
        this.authId = authId;
    }

    public long getProfileId() {
        return this.profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getUname() {
        return this.uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getVerificationCode() {
        return this.verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getChangeTime() {
        return this.changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Boolean getAccLock() {
        return accLock;
    }

    public void setAccLock(Boolean accLock) {
        this.accLock = accLock;
    }

    
    
}
