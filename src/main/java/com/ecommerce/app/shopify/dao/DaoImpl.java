package com.ecommerce.app.shopify.dao;

import com.ecommerce.app.shopify.domain.Authenticate;
import com.ecommerce.app.shopify.domain.Product;
import com.ecommerce.app.shopify.domain.Profile;
import com.ecommerce.app.shopify.util.Const;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public enum DaoImpl {

    INSTANCE;
    private static final Logger logger = Logger.getLogger(DaoImpl.class.getName());
    private static DataSource datasource;

    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            datasource = (DataSource) envContext.lookup("jdbc/shopify_db");
        } catch (NamingException ex) {
            Logger.getLogger(DaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Authenticate checkLogin(String user, String pwd) throws Exception {
        ResultSet resultSet = null;
        Authenticate auth = null;
        String query = "SELECT * FROM AUTHENTICATE where UNAME = ? and PWD = ?";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user);
            pstmt.setString(2, pwd);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                auth = new Authenticate(resultSet);
                return auth;
            }

        }
        return auth;
    }

    public Boolean lockAccount(String uname) throws Exception {

        String query = "UPDATE AUTHENTICATE SET ACC_LOCK = 1 where UNAME = ?";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            pstmt.setString(1, uname);
            Integer rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.log(Level.INFO, "Account locked, uname: {0}", uname);
                connection.commit();
                return true;
            }

        }
        return false;
    }

    public Boolean unlockAccount(Long profileId, String verificationCode) throws Exception {

        String query = "UPDATE AUTHENTICATE SET ACC_LOCK = 0 where PROFILE_ID = ? and VERIFICATION_CODE = ?";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            pstmt.setLong(1, profileId);
            pstmt.setString(2, verificationCode);
            Integer rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.log(Level.INFO, "Account un-locked, Profile Id: ", profileId);
                connection.commit();
                return true;
            }

        }
        return false;
    }

    public Boolean saveVerficiationCode(Long profileId, String verificationCode) throws Exception {
        String query = "UPDATE AUTHENTICATE SET VERIFICATION_CODE = ? where PROFILE_ID = ?";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            pstmt.setString(1, verificationCode);
            pstmt.setLong(2, profileId);
            Integer rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                return true;
            }

        }
        return false;
    }

    public Profile getProfile(Long profileId) throws Exception {
        ResultSet resultSet = null;
        Profile profile = null;
        String query = "SELECT * FROM PROFILE where PROFILE_ID = ?";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, profileId);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                profile = new Profile(resultSet);
                return profile;
            }

        }
        return profile;
    }

    public Boolean addIpToBlackList(String ipAddress) throws Exception {
        logger.log(Level.INFO, "Adding Black List Entry: {0}", ipAddress);
        this.deleteIp(ipAddress);
        String query = "INSERT INTO TRACK_LOGIN (IP_ADDRESS) VALUES(?)";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            pstmt.setString(1, ipAddress);
            Integer rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                return true;
            }
        }
        return false;
    }

    public Boolean deleteIp(String ipAddress) throws Exception {

        String query = "DELETE FROM TRACK_LOGIN  where IP_ADDRESS = ?";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            pstmt.setString(1, ipAddress);
            Integer rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                return true;
            }

        }
        return false;
    }

    public Boolean checkBlackList(String ipAddress) throws Exception {
        ResultSet resultSet = null;
        //Gives difference between current time and last login attempt in secs.
        String query = "SELECT {fn timestampdiff(SQL_TSI_SECOND, CHANGE_TIME,CURRENT_TIMESTAMP)} as DIFF FROM TRACK_LOGIN where IP_ADDRESS = ?";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, ipAddress);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Integer ttl = resultSet.getInt("DIFF");
                if (ttl > 0 && ttl <= Const.INSTANCE.IP_ADDR_TTL_SECS) {
                    return true;
                } else {
                    //Time has expired so delete the record. Its no longer black listed IP.
                    logger.log(Level.INFO, "Removing Black List Entry: {0}", ipAddress);
                    this.deleteIp(ipAddress);
                    return false;
                }

            }
        }
        return false;

    }

    public List<Product> getProducts() throws Exception {
        ResultSet resultSet = null;
        List<Product> productLst = new ArrayList();
        String query = "SELECT * FROM PRODUCT";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                productLst.add(new Product(resultSet));
            }

        }
        return productLst;
    }

    public Boolean saveProduct(Product product) throws Exception {
        logger.log(Level.INFO, "Adding Product: {0}", product.getName());
        String query = "INSERT INTO PRODUCT (CODE, NAME, CATEGORY, PRICE, DESCRIPTION, IMAGE) VALUES (?,?,?,?,?,?)";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            pstmt.setString(1, product.getCode());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getCategory());
            pstmt.setBigDecimal(4, product.getPrice());
            pstmt.setString(5, product.getDescription());
            pstmt.setBlob(6, product.getImagePart().getInputStream());

            Integer rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                return true;
            }
        }
        return false;
    }

    public Product getProductById(Long productId) throws Exception {
        ResultSet resultSet = null;
        String query = "SELECT * FROM PRODUCT where PRODUCT_ID = ?";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            pstmt.setLong(1, productId);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                return new Product(resultSet);
            }

        }
        return null;
    }
}