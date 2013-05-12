package com.ecommerce.app.shopify.domain;

import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.http.Part;

public class Product implements java.io.Serializable {

    private long productId;
    private String code;
    private String name;
    private String category;
    private Float price;
    private String description;
    private byte[] imageByteArry;
    private Part imagePart;
    private Date changeTime;

    public Product() {
    }

    @SuppressWarnings("empty-statement")
    public Product(ResultSet rs) throws SQLException, IOException {
        this.productId = rs.getLong("PRODUCT_ID");
        this.code = rs.getString("CODE");
        this.name = rs.getString("NAME");
        this.category = rs.getString("CATEGORY");
        this.price = rs.getFloat("PRICE");
        this.description = rs.getString("DESCRIPTION");

        Blob image = rs.getBlob("IMAGE");
        if (image != null) {
            int blobLength = (int) image.length();
            this.imageByteArry = image.getBytes(1, blobLength);
        }

        this.changeTime = rs.getTimestamp("CHANGE_TIME");
    }

    public Product(long productId, String code, String name, String category, Float price, String description, byte[] imageByteArry, Date changeTime) {
        this.productId = productId;
        this.code = code;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageByteArry = imageByteArry;
        this.changeTime = changeTime;
    }

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImageByteArry() {
        return imageByteArry;
    }

    public void setImageByteArry(byte[] imageByteArry) {
        this.imageByteArry = imageByteArry;
    }

    public Date getChangeTime() {
        return this.changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Part getImagePart() {
        return imagePart;
    }

    public void setImagePart(Part imagePart) {
        this.imagePart = imagePart;
    }
}
