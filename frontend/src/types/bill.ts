export interface Bill {
    id: number;
    code: string;
    productName: string;
    productDescription: string;
    productUnit: string;
    productCount: number;
    totalPrice: number;
    isPaid: 1 | 2;
    providerId: number;
}

export enum paymentStatus{
    PENDING = 1,
    PAID = 2,
}

export interface BillQueryParams {
    queryCode?: string;
    queryProductName?: string;
    queryProductDesc?: string;
    queryProviderCode?: string;
    queryProviderName?: string;
    queryIsPaid?: number;
    pageSize?: number;
    pageIndex?: number;
}

export interface BillListResponse {
    pageSize: number;
    totalPages: number;
    curPage: number;
    totalItems: number;
    bills: Bill[];
}

export interface PaymentStatus {
    status: 'unpaid' | 'paid'; // Assuming only two statuses
    count: number;
}

export interface ProductSale {
    productName: string;
    totalSalesAmount: number;
}

export interface BillStatsDto {
    paymentStatus: PaymentStatus[];
    productSales: ProductSale[];
}
