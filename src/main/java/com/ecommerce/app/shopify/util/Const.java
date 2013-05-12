package com.ecommerce.app.shopify.util;

public enum Const {

    INSTANCE;
    //Same entry in context.jspf please update that as well.
    public Integer CAPTACH_ATTEMPT_INTERVAL = 2;
    public Integer LOCK_ATTEMPT_INTERVAL = 3;
    public Integer IP_ATTEMPT_INTERVAL = 4;
    public Integer IP_ADDR_TTL_SECS = 60 * 60;
    public String PRIVATE_CAPTCHA_KEY = "6Lfi4uASAAAAAJaUdRAoHwIWYP3Qnb0Fvx7WOXqZ";
    public String TO_ADDR = "TO";
    public String BCC_ADDR = "BCC";
    public String CC_ADDR = "CC";
    public String SHIPPMENT_PENDING_DELIVERY  = "PENDING DELIVERY";
    public String SHIPPMENT_DISPATCHED = "PENDING DISPATCHED";
    public String SHIPPMENT_DELIVERED = "DELIVERED";
    public String ADMIN="ADMIN";
    public String USER="USER";
}
