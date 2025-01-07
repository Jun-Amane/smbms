/**
 * Package: moe.zzy040330.smbms.dto
 * File: ProviderStatsDto.java
 * Author: Ziyu ZHOU
 * Date: 07/01/2025
 * Time: 21:34
 * Description: Dto for provider statics
 */
package moe.zzy040330.smbms.dto;

import java.util.List;

public class ProviderStatsDto {
    private List<ProviderOrderContribution> providerOrderContributions;
    private List<ProviderSale> providerSales;

    public ProviderStatsDto() {
    }

    public ProviderStatsDto(List<ProviderOrderContribution> providerOrderContributions, List<ProviderSale> providerSales) {
        this.providerOrderContributions = providerOrderContributions;
        this.providerSales = providerSales;
    }

    public List<ProviderOrderContribution> getProviderOrderContributions() {
        return providerOrderContributions;
    }

    public void setProviderOrderContributions(List<ProviderOrderContribution> providerOrderContributions) {
        this.providerOrderContributions = providerOrderContributions;
    }

    public List<ProviderSale> getProviderSales() {
        return providerSales;
    }

    public void setProviderSales(List<ProviderSale> providerSales) {
        this.providerSales = providerSales;
    }

    public static class ProviderOrderContribution {
        private long providerId;
        private String providerName;
        private int orderCount;

        public ProviderOrderContribution() {
        }

        public ProviderOrderContribution(long providerId, String providerName, int orderCount) {
            this.providerId = providerId;
            this.providerName = providerName;
            this.orderCount = orderCount;
        }

        public long getProviderId() {
            return providerId;
        }

        public void setProviderId(long providerId) {
            this.providerId = providerId;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }
    }

    public static class ProviderSale {
        private long providerId;
        private String providerName;
        private double totalSalesAmount;

        public ProviderSale() {
        }

        public ProviderSale(long providerId, String providerName, double totalSalesAmount) {
            this.providerId = providerId;
            this.providerName = providerName;
            this.totalSalesAmount = totalSalesAmount;
        }

        public long getProviderId() {
            return providerId;
        }

        public void setProviderId(long providerId) {
            this.providerId = providerId;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public double getTotalSalesAmount() {
            return totalSalesAmount;
        }

        public void setTotalSalesAmount(double totalSalesAmount) {
            this.totalSalesAmount = totalSalesAmount;
        }
    }
}
