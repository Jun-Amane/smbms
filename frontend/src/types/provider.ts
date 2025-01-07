export interface Provider {
    id: number;
    code: string;
    name: string;
    description: string;
    address: string;
    phone: string;
    fax: string;
    contact: string;
}

export interface ProviderQueryParams {
    queryName?: string;
    queryCode?: string;
    pageSize?: number;
    pageIndex?: number;
}

export interface ProviderListResponse {
    pageSize: number;
    totalPages: number;
    curPage: number;
    totalItems: number;
    providers: Provider[];
}

interface ProviderOrderContribution {
    providerId: number;
    providerName: string;
    orderCount: number;
}

export interface ProviderSale {
    providerId: number;
    providerName: string;
    totalSalesAmount: number;
}

export interface ProviderStatsDto {
    providerOrderContributions: ProviderOrderContribution[];
    providerSales: ProviderSale[];
}
