DROP TABLE IF EXISTS smbms_bill;
DROP TABLE IF EXISTS smbms_address;
DROP TABLE IF EXISTS smbms_user;
DROP TABLE IF EXISTS smbms_role;
DROP TABLE IF EXISTS smbms_provider;

-- 由于外键约束，需要先创建被引用的表
CREATE TABLE smbms_role (
    id BIGINT NOT NULL AUTO_INCREMENT,
    roleCode VARCHAR(15) DEFAULT NULL,
    roleName VARCHAR(15) DEFAULT NULL,
    createdBy BIGINT DEFAULT NULL,
    creationDate DATETIME DEFAULT NULL,
    modifyBy BIGINT DEFAULT NULL,
    modifyDate DATETIME DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE smbms_provider (
    id BIGINT NOT NULL AUTO_INCREMENT,
    proCode VARCHAR(20) DEFAULT NULL,
    proName VARCHAR(20) DEFAULT NULL,
    proDesc VARCHAR(50) DEFAULT NULL,
    proContact VARCHAR(20) DEFAULT NULL,
    proPhone VARCHAR(20) DEFAULT NULL,
    proAddress VARCHAR(50) DEFAULT NULL,
    proFax VARCHAR(20) DEFAULT NULL,
    createdBy BIGINT DEFAULT NULL,
    creationDate DATETIME DEFAULT NULL,
    modifyDate DATETIME DEFAULT NULL,
    modifyBy BIGINT DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE smbms_user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    userCode VARCHAR(15) DEFAULT NULL,
    userName VARCHAR(15) DEFAULT NULL,
    userPassword VARCHAR(15) DEFAULT NULL,
    gender INT DEFAULT NULL,
    birthday DATE DEFAULT NULL,
    phone VARCHAR(15) DEFAULT NULL,
    address VARCHAR(30) DEFAULT NULL,
    userRole BIGINT DEFAULT NULL,
    createdBy BIGINT DEFAULT NULL,
    creationDate DATETIME DEFAULT NULL,
    modifyBy BIGINT DEFAULT NULL,
    modifyDate DATETIME DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE smbms_address (
    id BIGINT NOT NULL AUTO_INCREMENT,
    contact VARCHAR(15) DEFAULT NULL,
    addressDesc VARCHAR(50) DEFAULT NULL,
    postCode VARCHAR(15) DEFAULT NULL,
    tel VARCHAR(20) DEFAULT NULL,
    createdBy BIGINT DEFAULT NULL,
    creationDate DATETIME DEFAULT NULL,
    modifyBy BIGINT DEFAULT NULL,
    modifyDate DATETIME DEFAULT NULL,
    userId BIGINT DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE smbms_bill (
    id BIGINT NOT NULL AUTO_INCREMENT,
    billCode VARCHAR(20) DEFAULT NULL,
    productName VARCHAR(20) DEFAULT NULL,
    productDesc VARCHAR(50) DEFAULT NULL,
    productUnit VARCHAR(10) DEFAULT NULL,
    productCount DECIMAL(20,2) DEFAULT NULL,
    totalPrice DECIMAL(20,2) DEFAULT NULL,
    isPayment INT DEFAULT NULL,
    createdBy BIGINT DEFAULT NULL,
    creationDate DATETIME DEFAULT NULL,
    modifyBy BIGINT DEFAULT NULL,
    modifyDate DATETIME DEFAULT NULL,
    providerId BIGINT DEFAULT NULL,
    PRIMARY KEY (id)
);

-- 添加外键约束
ALTER TABLE smbms_address
    ADD CONSTRAINT fk_smbms_address_userid
    FOREIGN KEY (userId) REFERENCES smbms_user(id);

ALTER TABLE smbms_user
    ADD CONSTRAINT fk_smbms_user_userRole
    FOREIGN KEY (userRole) REFERENCES smbms_role(id);

ALTER TABLE smbms_bill
    ADD CONSTRAINT fk_smbms_bill_providerid
    FOREIGN KEY (providerId) REFERENCES smbms_provider(id);