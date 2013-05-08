package com.ecommerce.app.shopify.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Profile implements java.io.Serializable {

    private long profileId;
    private String uname;
    private String pwd;
    private String verificationCode;
    private Boolean accLock;
    private String name;
    private String gender;
    private String email;
    private String address;
    private String city;
    private String state;
    private String country;
    private Long pincode;
    private Long mobile;
    private String status;
    private String urole;
    private Date changeTime;

    public Profile() {
    }

    public Profile(ResultSet rs) throws SQLException {
        this.profileId = rs.getInt("PROFILE_ID");

        this.uname = rs.getString("UNAME");
        this.pwd = rs.getString("PWD");
        this.verificationCode = rs.getString("VERIFICATION_CODE");
        this.accLock = rs.getBoolean("ACC_LOCK");

        this.name = rs.getString("NAME");
        this.gender = rs.getString("GENDER");
        this.email = rs.getString("EMAIL");
        this.address = rs.getString("ADDRESS");
        this.city = rs.getString("CITY");
        this.state = rs.getString("STATE");
        this.country = rs.getString("COUNTRY");
        this.pincode = rs.getLong("PINCODE");
        this.mobile = rs.getLong("MOBILE");
        this.status = rs.getString("STATUS");
        this.urole = rs.getString("UROLE");
        this.changeTime = rs.getTimestamp("CHANGE_TIME");
    }

    public Profile(String uname, String pwd, String verificationCode, Boolean accLock, String name, String gender, String email, String address, String city, String state, String country, Long pincode, Long mobile, String status, String urole) {

        this.uname = uname;
        this.pwd = pwd;
        this.verificationCode = verificationCode;
        this.accLock = accLock;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.mobile = mobile;
        this.status = status;
        this.urole = urole;

    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Boolean getAccLock() {
        return accLock;
    }

    public void setAccLock(Boolean accLock) {
        this.accLock = accLock;
    }

    public long getProfileId() {
        return this.profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPincode() {
        return this.pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public Long getMobile() {
        return this.mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getChangeTime() {
        return this.changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getUrole() {
        return urole;
    }

    public void setUrole(String urole) {
        this.urole = urole;
    }
}
