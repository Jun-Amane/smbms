package moe.zzy040330.smbms.dto;

//import lombok.Data;

import java.math.BigDecimal;
//@Data
public class BillRequest {
    private String productName;      // 商品名称
    private String productDesc;      // 商品描述
    private String productUnit;      // 商品单位（例如：个/箱）
    private BigDecimal productCount; // 商品数量
    private BigDecimal totalPrice;   // 总价
    private Long providerId;         // 供应商ID
    private Boolean isPaid;          // 是否付款

    public BillRequest() {
    }

    public BillRequest(String productDesc, String productName, String productUnit, BigDecimal productCount, BigDecimal totalPrice, Long providerId, Boolean isPaid) {
        this.productDesc = productDesc;
        this.productName = productName;
        this.productUnit = productUnit;
        this.productCount = productCount;
        this.totalPrice = totalPrice;
        this.providerId = providerId;
        this.isPaid = isPaid;
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

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}

