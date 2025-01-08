import axios from '@/lib/axios';
import {Provider, ProviderQueryParams, ProviderListResponse, ProviderStatsDto} from "@/types";

export const providerService = {

    getProviders: async (params: ProviderQueryParams): Promise<ProviderListResponse> => {
        const response = await axios.get('/api/provider', {params});
        return response.data;
    },

    createProvider: async (provider: Omit<Provider, 'id'>): Promise<void> => {
        await axios.post('/api/provider', provider);
    },

    getProvider: async (id: number): Promise<Provider> => {
        const response = await axios.get(`/api/provider/${id}`);
        return response.data;
    },

    updateProvider: async (id: number, provider: Partial<Provider>): Promise<void> => {
        await axios.put(`/api/provider/${id}`, provider);
    },

    deleteProvider: async (id: number): Promise<void> => {
        await axios.delete(`/api/provider/${id}`);
    },

    getProviderStats: async(): Promise<ProviderStatsDto> => {
        const response = await axios.get(`/api/provider/stats`);
        return response.data;
    }

};
