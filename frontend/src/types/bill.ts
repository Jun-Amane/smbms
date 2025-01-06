export interface Bill {
    id: number;
    code: string;
    productName: string;
    productDescription: string;
    productUnit: string;
    productCount: number;
    totalPrice: number;
    isPaid: 0 | 1;
    providerId: number;
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
    providers: Bill[];
}
