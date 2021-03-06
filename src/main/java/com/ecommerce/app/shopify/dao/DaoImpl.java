package com.ecommerce.app.shopify.dao;

import com.ecommerce.app.shopify.domain.LineItems;
import com.ecommerce.app.shopify.domain.Product;
import com.ecommerce.app.shopify.domain.Profile;
import com.ecommerce.app.shopify.domain.SaleOrder;
import com.ecommerce.app.shopify.util.Const;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

    public Profile checkLogin(String user, String pwd) throws Exception {
        ResultSet resultSet = null;
        Profile profile = null;
        String query = "SELECT * FROM PROFILE where UNAME = ? and PWD = ?";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user);
            pstmt.setString(2, pwd);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                profile = new Profile(resultSet);
                return profile;
            }

        }
        return profile;
    }

    public Boolean lockAccount(String uname) throws Exception {

        String query = "UPDATE PROFILE SET ACC_LOCK = 1 where UNAME = ?";
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

        String query = "UPDATE PROFILE SET ACC_LOCK = 0 where PROFILE_ID = ? and VERIFICATION_CODE = ?";
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
        String query = "UPDATE PROFILE SET VERIFICATION_CODE = ? where PROFILE_ID = ?";
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

    public Profile getProfileById(Long profileId) throws Exception {
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

    public Profile getProfileByName(String uname) throws Exception {
        ResultSet resultSet = null;
        Profile profile = null;
        String query = "SELECT * FROM PROFILE where UNAME = ?";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, uname);
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

    public List<Product> getAllProducts() throws Exception {
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
            pstmt.setFloat(4, product.getPrice());
            pstmt.setString(5, product.getDescription());
            if (product.getImagePart() != null) {
                pstmt.setBlob(6, product.getImagePart().getInputStream());
            } else {
                pstmt.setNull(6, java.sql.Types.BLOB);
            }


            Integer rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                return true;
            }
        }
        return false;
    }

    public Boolean updateProduct(Product product) throws Exception {
        logger.log(Level.INFO, "Updating Product: {0}", product.getName());
        String query;
        if (product.getImagePart() != null) {
            query = "UPDATE PRODUCT SET CODE = ?, NAME =? , CATEGORY=?, PRICE=?, DESCRIPTION=?, IMAGE=? where PRODUCT_ID = ?";
        } else {
            query = "UPDATE PRODUCT SET CODE = ?, NAME =? , CATEGORY=?, PRICE=?, DESCRIPTION=? where PRODUCT_ID = ?";
        }

        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            pstmt.setString(1, product.getCode());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getCategory());
            pstmt.setFloat(4, product.getPrice());
            pstmt.setString(5, product.getDescription());
            if (product.getImagePart() != null) {
                pstmt.setBlob(6, product.getImagePart().getInputStream());
                pstmt.setLong(7, product.getProductId());
            } else {
                pstmt.setLong(6, product.getProductId());
            }

            Integer rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                return true;
            }
        }
        return false;
    }

    public Boolean deleteProduct(Long productId) throws Exception {
        logger.log(Level.INFO, "Deleting Product Id: {0}", productId);
        String query = "DELETE FROM PRODUCT where PRODUCT_ID = ?";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            pstmt.setLong(1, productId);
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

    public Boolean saveProfile(Profile profile) throws Exception {
        logger.log(Level.INFO, "Adding User: {0}", profile.getName());
        String query = "INSERT INTO PROFILE (UNAME,PWD,VERIFICATION_CODE,ACC_LOCK,NAME,GENDER,EMAIL,ADDRESS,CITY,STATE,COUNTRY,PINCODE,MOBILE,STATUS,UROLE,CHANGE_TIME) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,DEFAULT)";
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query);) {
            connection.setAutoCommit(false);

            pstmt.setString(1, profile.getUname());
            pstmt.setString(2, profile.getPwd());
            pstmt.setString(3, profile.getVerificationCode());
            pstmt.setBoolean(4, profile.getAccLock());
            pstmt.setString(5, profile.getName());
            pstmt.setString(6, profile.getGender());
            pstmt.setString(7, profile.getEmail());
            pstmt.setString(8, profile.getAddress());
            pstmt.setString(9, profile.getCity());
            pstmt.setString(10, profile.getState());
            pstmt.setString(11, profile.getCountry());
            pstmt.setLong(12, profile.getPincode());
            pstmt.setLong(13, profile.getMobile());
            pstmt.setString(14, profile.getStatus());
            pstmt.setString(15, profile.getUrole());

            Integer rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                return true;
            }

        }
        return false;
    }

    public Boolean updateProfile(Profile profile) throws Exception {
        logger.log(Level.INFO, "Update User: {0}", profile.getName());

        if (profile.getProfileId() != 0l) {
            String query = "UPDATE PROFILE SET UNAME = ?,PWD =?,NAME=?,GENDER=?,EMAIL=?,ADDRESS=?,CITY=?,STATE=?,COUNTRY=?,PINCODE=?,MOBILE=?,STATUS=?,CHANGE_TIME=CURRENT_TIMESTAMP where PROFILE_ID = ?";
            try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query);) {
                connection.setAutoCommit(false);

                pstmt.setString(1, profile.getUname());
                pstmt.setString(2, profile.getPwd());
                pstmt.setString(3, profile.getName());
                pstmt.setString(4, profile.getGender());
                pstmt.setString(5, profile.getEmail());
                pstmt.setString(6, profile.getAddress());
                pstmt.setString(7, profile.getCity());
                pstmt.setString(8, profile.getState());
                pstmt.setString(9, profile.getCountry());
                pstmt.setLong(10, profile.getPincode());
                pstmt.setLong(11, profile.getMobile());
                pstmt.setString(12, profile.getStatus());
                pstmt.setLong(13, profile.getProfileId());
                Integer rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    connection.commit();
                    return true;
                }

            }
        }
        return false;
    }

    public Boolean saveOrder(SaleOrder saleOrder, List<LineItems> lineItemsLst) throws Exception {

        logger.log(Level.INFO, "Saving Sale Order");

        String query1 = "INSERT INTO SALE_ORDER (PROFILE_ID,ORDER_TIME,ORDER_STATUS,DELIVERY_TIME_START,DELIVERY_TIME_END,CHANGE_TIME) VALUES (?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,DEFAULT)";
        String query2 = "INSERT INTO LINE_ITEMS (PRODUCT_ID,ORDER_ID,QTY,CHANGE_TIME) VALUES (?,?,?,DEFAULT)";

        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS); PreparedStatement pstmt2 = connection.prepareStatement(query2);) {
            connection.setAutoCommit(false);

            pstmt1.setLong(1, saleOrder.getProfileId());
            pstmt1.setString(2, saleOrder.getOrderStatus());


            Integer rowsAffected = pstmt1.executeUpdate();
            if (rowsAffected > 0) {

                ResultSet rs = pstmt1.getGeneratedKeys();
                rs.next();
                Long saleOrderId = rs.getLong(1);
                saleOrder.setOrderId(saleOrderId);

                for (LineItems lineItem : lineItemsLst) {
                    pstmt2.setLong(1, lineItem.getProductId());
                    pstmt2.setLong(2, saleOrderId);
                    pstmt2.setInt(3, lineItem.getQty());
                    pstmt2.executeUpdate();
                }
                connection.commit();
                return true;
            }


        }
        return false;
    }

    public List<SaleOrder> getSaleOrderByProfileId(Long profileId) throws Exception {
        ResultSet resultSet = null;
        Profile profile = this.getProfileById(profileId);
        String query;
        if (profile.getUrole().equals(Const.INSTANCE.ADMIN)) {
            //if admin load all order details.
            query = "SELECT * FROM SALE_ORDER";
        } else {
            //if single user load only his orders.
            query = "SELECT * FROM SALE_ORDER where PROFILE_ID = " + profileId;

        }

        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            List<SaleOrder> saleOrderLst = new ArrayList();
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                saleOrderLst.add(new SaleOrder(resultSet));

            }
            return saleOrderLst;
        }

    }

    public List<Profile> getAllProfiles() throws Exception {
        ResultSet resultSet = null;
        String query = "SELECT * FROM PROFILE";
        List<Profile> profileLst = new ArrayList<Profile>();
        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                profileLst.add(new Profile(resultSet));
            }

            return profileLst;
        }

    }

    public List<LineItems> getAllLineItemByProfileId(Long profileId) throws Exception {
        ResultSet resultSet = null;
        Profile profile = this.getProfileById(profileId);
        String query;
        if (profile.getUrole().equals(Const.INSTANCE.ADMIN)) {
            //if admin load all order details.
            query = "SELECT * FROM LINE_ITEMS";
        } else {
            //if single user load only his orders.
            query = "SELECT * FROM LINE_ITEMS,SALE_ORDER where LINE_ITEMS.ORDER_ID = SALE_ORDER.ORDER_ID AND SALE_ORDER.PROFILE_ID = " + profileId;

        }

        try (Connection connection = datasource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            List<LineItems> lineItemsLst = new ArrayList();
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                lineItemsLst.add(new LineItems(resultSet));

            }
            return lineItemsLst;
        }

    }
}