package moe.zzy040330.smbms.dto;

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
    private String productUnit;       // Product unit
    private BigDecimal totalPrice;     // Total price of the bill
    private Integer isPaid;            // Payment status: 0 for unpaid, 1 for paid
    private Long providerId;           // ID of the provider
    private String providerName;       // Name of the provider

    public BillDto() {
    }

    public BillDto(Long id, String billCode, String productName, String productDesc, String productUnit, BigDecimal totalPrice, Integer isPaid, Long providerId, String providerName) {
        this.id = id;
        this.billCode = billCode;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productUnit=productUnit;
        this.totalPrice = totalPrice;
        this.isPaid = isPaid;
        this.providerId = providerId;
        this.providerName = providerName;
    }


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

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
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

    public Long getProviderId() {
        return providerId;

        /* TODO: 这里返回值类型改成Long， 与成员保持一致。
        *   调用处，若需要从BillDto构建Bill对象，
        *       1. 新建一个Provider实体： var provider = new Provider()
        *       2.  provider.setId(xxx) <-- 这里通常只需要设置Id一个字段，具体见service和mapper的底层实现
        *       3. bill.setProvider(provider)
        *   NOTE: 上述逻辑在UserController中也有，建议参考一下UserController与ProviderController的设计与实现，
        *         它们的逻辑基本一致。
        *
        * */

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

}
