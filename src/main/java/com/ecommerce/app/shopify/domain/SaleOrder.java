package com.ecommerce.app.shopify.domain;

import java.util.Date;

public class SaleOrder implements java.io.Serializable {

    private long orderId;
    private long profileId;
    private Date orderTime;
    private String orderStatus;
    private Date deliveryTimeStart;
    private Date deliveryTimeEnd;
    private Date changeTime;

    public SaleOrder() {
    }

    public SaleOrder(long orderId, long profileId, Date orderTime, String orderStatus, Date deliveryTimeStart, Date deliveryTimeEnd, Date changeTime) {
        this.orderId = orderId;
        this.profileId = profileId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.deliveryTimeStart = deliveryTimeStart;
        this.deliveryTimeEnd = deliveryTimeEnd;
        this.changeTime = changeTime;
    }

    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProfileId() {
        return this.profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public Date getOrderTime() {
        return this.orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getDeliveryTimeStart() {
        return this.deliveryTimeStart;
    }

    public void setDeliveryTimeStart(Date deliveryTimeStart) {
        this.deliveryTimeStart = deliveryTimeStart;
    }

    public Date getDeliveryTimeEnd() {
        return this.deliveryTimeEnd;
    }

    public void setDeliveryTimeEnd(Date deliveryTimeEnd) {
        this.deliveryTimeEnd = deliveryTimeEnd;
    }

    public Date getChangeTime() {
        return this.changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
}
