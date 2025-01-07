/**
 * Package: moe.zzy040330.smbms.dto
 * File: DashboardStatsDto.java
 * Author: Ziyu ZHOU
 * Date: 07/01/2025
 * Time: 21:42
 * Description: Dto for dashboard main statics
 */
package moe.zzy040330.smbms.dto;

import java.util.List;

public class DashboardStatsDto {
    private int orderCount;
    private double totalOrderAmount;
    private int providerCount;
    private int userCount;
    private List<QuarterlyStat> quarterlyStats;

    public DashboardStatsDto() {
    }

    public DashboardStatsDto(int orderCount, double totalOrderAmount, int providerCount, int userCount, List<QuarterlyStat> quarterlyStats) {
        this.orderCount = orderCount;
        this.totalOrderAmount = totalOrderAmount;
        this.providerCount = providerCount;
        this.userCount = userCount;
        this.quarterlyStats = quarterlyStats;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(double totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public int getProviderCount() {
        return providerCount;
    }

    public void setProviderCount(int providerCount) {
        this.providerCount = providerCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public List<QuarterlyStat> getQuarterlyStats() {
        return quarterlyStats;
    }

    public void setQuarterlyStats(List<QuarterlyStat> quarterlyStats) {
        this.quarterlyStats = quarterlyStats;
    }

    public static class QuarterlyStat {
        private String quarter;
        private int orderCount;
        private double totalAmount;

        public QuarterlyStat() {
        }

        public QuarterlyStat(String quarter, int orderCount, double totalAmount) {
            this.quarter = quarter;
            this.orderCount = orderCount;
            this.totalAmount = totalAmount;
        }

        public String getQuarter() {
            return quarter;
        }

        public void setQuarter(String quarter) {
            this.quarter = quarter;
        }

        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }
    }
}
