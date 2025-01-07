package moe.zzy040330.smbms.dto;

import java.math.BigDecimal;

/**
 * BillDto is a Data Transfer Object (DTO) representing a Bill entity.
 * It is used to carry data between processes while keeping entity-related details hidden.
 */
public class BillDto {

    private Long id;
    private String code;           // Bill code
    private String productName;        // Product name
    private String productDescription;        // Product description
    private String productUnit;
    private BigDecimal productCount;// Product unit
    private BigDecimal totalPrice;     // Total price of the bill
    private Integer isPaid;            // Payment status: 0 for unpaid, 1 for paid
    private Long providerId;           // ID of the provider
          // Name of the provider


    public BillDto() {
    }

    public BillDto(Long id, String code, String productName, String productDescription, String productUnit, BigDecimal productCount, BigDecimal totalPrice, Integer isPaid, Long providerId) {
        this.id = id;
        this.code = code;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productUnit = productUnit;
        this.productCount = productCount;
        this.totalPrice = totalPrice;
        this.isPaid = isPaid;
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

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }
}
