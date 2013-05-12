package com.ecommerce.app.shopify.domain;

import java.util.Date;

public class TrackLogin implements java.io.Serializable {

    private long trackId;
    private String ipAddress;
    private Date changeTime;

    public TrackLogin() {
    }

    public TrackLogin(long trackId, String ipAddress, Date changeTime) {
        this.trackId = trackId;
        this.ipAddress = ipAddress;
        this.changeTime = changeTime;
    }

    public long getTrackId() {
        return this.trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getChangeTime() {
        return this.changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
}
