export interface QuarterlyStat {
    quarter: string;
    orderCount: number;
    totalAmount: number;
}

export interface DashboardStatsDto {
    orderCount: number;
    totalOrderAmount: number;
    providerCount: number;
    userCount: number;
    quarterlyStats: QuarterlyStat[];
}
