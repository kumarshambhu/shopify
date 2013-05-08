package com.ecommerce.app.shopify.test;

import com.ecommerce.app.shopify.dao.DaoImpl;
import com.ecommerce.app.shopify.domain.Profile;

public class DBTest {

    public static void main(String[] args) throws Exception {

        Profile profile = DaoImpl.INSTANCE.checkLogin("admin", "admin");
        System.out.println(profile.getProfileId());


    }
}
