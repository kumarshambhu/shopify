package com.ecommerce.app.shopify.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class LineItems implements java.io.Serializable {

    private long lineItemId;
    private long orderId;
    private long productId;
    private int qty;
    private Date changeTime;
    //Virtual columns - These need not be in DB.
    private String name;
    private Float price;

    public LineItems() {
    }

    public LineItems(long itemId, long orderId, long productId, int qty, Date changeTime) {
        this.orderId = orderId;
        this.productId = productId;
        this.qty = qty;
        this.changeTime = changeTime;
    }

    public LineItems(ResultSet rs) throws SQLException {
        this.lineItemId = rs.getLong("LINE_ITEM_ID");
        this.orderId = rs.getLong("ORDER_ID");
        this.productId = rs.getLong("PRODUCT_ID");
        this.qty = rs.getInt("QTY");
        this.changeTime = rs.getTimestamp("CHANGE_TIME");
    }

    public long getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(long lineItemId) {
        this.lineItemId = lineItemId;
    }

    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQty() {
        return this.qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Date getChangeTime() {
        return this.changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
