import axios from '@/lib/axios';
import { User, Role, UserQueryParams, UserListResponse } from '@/types/user';
import {PasswordUpdateRequest} from "@/types";

export const userService = {
    // get user list
    getUsers: async (params: UserQueryParams): Promise<UserListResponse> => {
        const response = await axios.get('/user', { params });
        return response.data;
    },

    // get rolelist
    getRoles: async (): Promise<Role[]> => {
        const response = await axios.get('/user/rolelist');
        return response.data;
    },

    // get user by id
    getUser: async (id: number): Promise<User> => {
        const response = await axios.get(`/user/${id}`);
        return response.data;
    },

    // create user
    createUser: async (user: Omit<User, 'id'>): Promise<void> => {
        await axios.post('/user', user);
    },

    // update user
    updateUser: async (id: number, user: Partial<User>): Promise<void> => {
        await axios.put(`/user/${id}`, user);
    },

    // delete user
    deleteUser: async (id: number): Promise<void> => {
        await axios.delete(`/user/${id}`);
    },

    // check if user-code exists
    checkCodeExists: async (code: string): Promise<{ result: string }> => {
        const response = await axios.get('/user/codeexists', {
            params: { code }
        });
        return response.data;
    },

    // change password
    updatePassword: async (id: number, passwordReq: PasswordUpdateRequest): Promise<void> => {
        await axios.patch(`/user/${id}/password`, passwordReq);
    },
};
