package com.ecommerce.app.shopify.test;

import com.ecommerce.app.shopify.dao.DaoImpl;
import com.ecommerce.app.shopify.domain.Authenticate;

public class DBTest {

    public static void main(String[] args) throws Exception {
        
        Authenticate auth = DaoImpl.INSTANCE.checkLogin("admin", "admin");
        System.out.println(auth.getProfileId());


    }
}
