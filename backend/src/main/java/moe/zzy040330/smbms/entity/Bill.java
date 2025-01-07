/**
 * Package: moe.zzy040330.smbms.entity
 * File: Bill.java
 * Author: Mingxue Li
 * Date: 05/01/2025
 * Description: This class represents a Bill entity with basic information.
 */

package moe.zzy040330.smbms.entity;

import java.util.Date;
import java.math.BigDecimal;

public class Bill {
    private Long id;// Unique identifier for the bill.
    private String code;// Code representing the bill.
    private String productName;// Name of the product described in the bill.
    private String productDescription;//Description of the product.
    private String productUnit;//Unit of measurement for the product.
    private BigDecimal productCount;//Quantity of the product.
    private BigDecimal totalPrice;// Total price for the billed products.
    private Integer isPaid;// Payment status indicator.
    private Provider provider;// Identifier for the provider of the product.
    private User createdBy;//User ID of the person who created the bill.
    private Date creationDate;// Date and time when the bill was created.
    private User modifiedBy;// User ID of the person who last modified the bill.
    private Date modificationDate;// Date and time when the bill was last updated.
    private Long providerId;
    public Bill() {
    }

    public Bill(Long id, String code, String productName, String productDescription, String productUnit, BigDecimal productCount, BigDecimal totalPrice, Integer isPaid, Provider provider, User createdBy, Date creationDate, User modifiedBy, Date modificationDate,Long providerId) {
        this.id = id;
        this.code = code;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productUnit = productUnit;
        this.productCount = productCount;
        this.totalPrice = totalPrice;
        this.isPaid = isPaid;
        this.provider = provider;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifiedBy = modifiedBy;
        this.modificationDate = modificationDate;
        this.providerId=providerId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public BigDecimal getProductCount() {
        return productCount;
    }

    public void setProductCount(BigDecimal productCount) {
        this.productCount = productCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

}