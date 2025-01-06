package moe.zzy040330.smbms.dto;

import moe.zzy040330.smbms.entity.Provider;

import java.math.BigDecimal;
import java.util.Date;

/**
 * BillDto is a Data Transfer Object (DTO) representing a Bill entity.
 * It is used to carry data between processes while keeping entity-related details hidden.
 */
public class BillDto {

    private Long id;
    private String billCode;           // Bill code
    private String productName;        // Product name
    private String productDesc;        // Product description
    private Integer productUnit;       // Product unit
    private BigDecimal totalPrice;     // Total price of the bill
    private Integer isPaid;            // Payment status: 0 for unpaid, 1 for paid
    private Long providerId;           // ID of the provider
    private String providerName;       // Name of the provider
    private Long createdBy;            // User ID of the creator
    private Date creationDate;         // Date of creation
    private Long modifiedBy;           // User ID of the last modifier
    private Date modificationDate;     // Date of last modification

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Integer getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(Integer productUnit) {
        this.productUnit = productUnit;
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

    public Provider getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}
