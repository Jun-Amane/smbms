/**
 * Package: moe.zzy040330.smbms.dto
 * File: BillStatsDto.java
 * Author: Ziyu ZHOU
 * Date: 07/01/2025
 * Time: 21:36
 * Description: Dto for bill statistic
 */
package moe.zzy040330.smbms.dto;

import java.util.List;

public class BillStatsDto {
    private List<PaymentStatus> paymentStatus;
    private List<ProductSale> productSales;

    public BillStatsDto() {
    }

    public BillStatsDto(List<PaymentStatus> paymentStatus, List<ProductSale> productSales) {
        this.paymentStatus = paymentStatus;
        this.productSales = productSales;
    }

    public List<PaymentStatus> getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(List<PaymentStatus> paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<ProductSale> getProductSales() {
        return productSales;
    }

    public void setProductSales(List<ProductSale> productSales) {
        this.productSales = productSales;
    }

    public static class PaymentStatus {
        private String status;
        private int count;

        public PaymentStatus() {
        }

        public PaymentStatus(String status, int count) {
            this.status = status;
            this.count = count;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class ProductSale {
        private String productName;
        private double totalSalesAmount;

        public ProductSale() {
        }

        public ProductSale(String productName, double totalSalesAmount) {
            this.productName = productName;
            this.totalSalesAmount = totalSalesAmount;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getTotalSalesAmount() {
            return totalSalesAmount;
        }

        public void setTotalSalesAmount(double totalSalesAmount) {
            this.totalSalesAmount = totalSalesAmount;
        }
    }
}
