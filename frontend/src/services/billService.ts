import axios from '@/lib/axios';
import {Bill, BillQueryParams, BillListResponse, BillStatsDto} from "@/types/bill";
import {Provider} from "@/types/provider";

export const billService = {

    getBills: async (params:BillQueryParams): Promise<BillListResponse> => {
        const response = await axios.get('/api/bill', {params});
        return response.data;
    },

    createBill: async (bill: Omit<Bill, 'id'>): Promise<void> => {
        await axios.post('/api/bill', bill);
    },

    getBillById: async (id: number): Promise<Bill> => {
        const response = await axios.get(`/api/bill/${id}`);
        return response.data;
    },

    updateBill: async (id: number, bill: Partial<Bill>): Promise<void> => {
        await axios.put(`/api/bill/${id}`, bill);
    },

    deleteBill: async(id: number): Promise<void> => {
        await axios.delete(`/api/bill/${id}`);
    },

    getListOfProviders: async(): Promise<Provider[]> => {
        const response = await axios.get('/api/bill/providerlist');
        return response.data;
    },

    getBillStats: async(): Promise<BillStatsDto> => {
        const response = await axios.get('/api/bill/stats');
        return response.data;
    }

};
