
DROP TABLE TRACK_LOGIN;
DROP TABLE LINE_ITMES;
DROP TABLE SALE_ORDER;
DROP TABLE PRODUCT;
DROP TABLE PROFILE;

CREATE TABLE PROFILE
(
	PROFILE_ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
	UNAME VARCHAR(100) NOT NULL,
	PWD VARCHAR(100),
	VERIFICATION_CODE VARCHAR(100),
	ACC_LOCK SMALLINT,
	NAME VARCHAR(100),
	GENDER CHAR(1) NOT NULL CONSTRAINT GENDER_CONSTRAINT CHECK (GENDER IN ('M', 'F')),
	EMAIL VARCHAR(100),
	ADDRESS VARCHAR(200),
	CITY VARCHAR(100),
	STATE VARCHAR(100),
	COUNTRY VARCHAR(100),
	PINCODE NUMERIC(10),
	MOBILE NUMERIC(10),
	STATUS VARCHAR(10),
        UROLE VARCHAR(100) NOT NULL,
	CHANGE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE TRACK_LOGIN
(
	TRACK_ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
	IP_ADDRESS VARCHAR(100) NOT NULL,
	CHANGE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE PRODUCT
(
	PRODUCT_ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
	CODE VARCHAR(100) NOT NULL,
	NAME VARCHAR(100) NOT NULL,
	CATEGORY VARCHAR(100) NOT NULL,
	PRICE DECIMAL(8,2),
	DESCRIPTION VARCHAR(500) NOT NULL,
	IMAGE BLOB,
	CHANGE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	
);


CREATE TABLE SALE_ORDER
(
	ORDER_ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
	PROFILE_ID BIGINT NOT NULL,
	ORDER_TIME TIMESTAMP NOT NULL,
	ORDER_STATUS VARCHAR(100) NOT NULL,
	DELIVERY_TIME_START TIMESTAMP NOT NULL,
	DELIVERY_TIME_END TIMESTAMP NOT NULL,
	CHANGE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY(PROFILE_ID) REFERENCES PROFILE(PROFILE_ID)
	
);

CREATE TABLE LINE_ITMES
(
	PRODUCT_ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
	ORDER_ID BIGINT NOT NULL,
	QTY INTEGER NOT NULL,
	CHANGE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY(ORDER_ID) REFERENCES SALE_ORDER(ORDER_ID),
	FOREIGN KEY(PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID)
	
);

INSERT INTO SHOPIFY_USER.PROFILE (UNAME,PWD,VERIFICATION_CODE,ACC_LOCK,"NAME", GENDER, EMAIL, ADDRESS, CITY, "STATE", COUNTRY, PINCODE, MOBILE, STATUS, UROLE,CHANGE_TIME) 
	VALUES ('admin', 'admin', '',0 ,'Admin', 'M', 'shopify.ind@gmail.com', '', 'Mysore', 'Karnataka', 'India', 560001, 999999999, 'Active', 'ADMIN',DEFAULT);

	
INSERT INTO SHOPIFY_USER.PROFILE (UNAME,PWD,VERIFICATION_CODE,ACC_LOCK,"NAME", GENDER, EMAIL, ADDRESS, CITY, "STATE", COUNTRY, PINCODE, MOBILE, STATUS, CHANGE_TIME,UROLE) 
	VALUES ('kalyani', 'password123', '',0,'Kalyani', 'F', 'shopify.ind@gmail.com', '', 'Mysore', 'Karnataka', 'India', 560001, 999999999, 'Active', DEFAULT,'USER');


INSERT INTO SHOPIFY_USER.PRODUCT (CODE, "NAME", CATEGORY, PRICE, DESCRIPTION, IMAGE, CHANGE_TIME) 
	VALUES ('T001', 'Mens T Shirt Red - Size 40', 'Shirts', 300, 'Adidas', NULL, DEFAULT);

INSERT INTO SHOPIFY_USER.PRODUCT (CODE, "NAME", CATEGORY, PRICE, DESCRIPTION, IMAGE, CHANGE_TIME) 
	VALUES ('T002', 'Mens T Shirt Green - Size 32', 'Shirts', 250, 'Adidas', NULL, DEFAULT);

INSERT INTO SHOPIFY_USER.PRODUCT (CODE, "NAME", CATEGORY, PRICE, DESCRIPTION, IMAGE, CHANGE_TIME) 
	VALUES ('T003', 'Mens T Shirt Orange - Size 42', 'Shirts', 900, 'Adidas', NULL, DEFAULT);

INSERT INTO SHOPIFY_USER.PRODUCT (CODE, "NAME", CATEGORY, PRICE, DESCRIPTION, IMAGE, CHANGE_TIME) 
	VALUES ('K001', 'Womens Kurtha Red - Size M', 'Kurhta', 370, 'Soch', NULL, DEFAULT);

INSERT INTO SHOPIFY_USER.PRODUCT (CODE, "NAME", CATEGORY, PRICE, DESCRIPTION, IMAGE, CHANGE_TIME) 
	VALUES ('K002', 'Womens Kurtha Green - Size L', 'Kurhta', 600, 'Soch', NULL, DEFAULT);

INSERT INTO SHOPIFY_USER.PRODUCT (CODE, "NAME", CATEGORY, PRICE, DESCRIPTION, IMAGE, CHANGE_TIME) 
	VALUES ('K003', 'Womens Kurtha Pink - Size S', 'Kurhta', 420, 'Soch', NULL, DEFAULT);

INSERT INTO SHOPIFY_USER.PRODUCT (CODE, "NAME", CATEGORY, PRICE, DESCRIPTION, IMAGE, CHANGE_TIME) 
	VALUES ('B001', 'Womens Hand Bag - Size S', 'Bags', 380, 'Adidas', NULL, DEFAULT);

INSERT INTO SHOPIFY_USER.PRODUCT (CODE, "NAME", CATEGORY, PRICE, DESCRIPTION, IMAGE, CHANGE_TIME) 
	VALUES ('S001', 'Nike Running Shoes - Size 9', 'Bags', 1280, 'Nike', NULL, DEFAULT);

INSERT INTO SHOPIFY_USER.PRODUCT (CODE, "NAME", CATEGORY, PRICE, DESCRIPTION, IMAGE, CHANGE_TIME) 
	VALUES ('S002', 'Nike Running Shoes Spring - Size 11', 'Bags', 2330, 'Nike', NULL, DEFAULT);

