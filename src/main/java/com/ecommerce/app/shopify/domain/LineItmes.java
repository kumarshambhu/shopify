package com.ecommerce.app.shopify.domain;
// Generated May 3, 2013 2:55:16 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * LineItmes generated by hbm2java
 */
public class LineItmes  implements java.io.Serializable {


     private long itemId;
     private long orderId;
     private long productId;
     private int qty;
     private Date changeTime;

    public LineItmes() {
    }

    public LineItmes(long itemId, long orderId, long productId, int qty, Date changeTime) {
       this.itemId = itemId;
       this.orderId = orderId;
       this.productId = productId;
       this.qty = qty;
       this.changeTime = changeTime;
    }
   
    public long getItemId() {
        return this.itemId;
    }
    
    public void setItemId(long itemId) {
        this.itemId = itemId;
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




}


