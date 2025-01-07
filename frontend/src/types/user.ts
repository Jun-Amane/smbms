export interface UserRequest {
    id?: number;
    code: string;
    name: string;
    password?: string;
    gender: 1 | 2 | 0;
    birthday: string;
    phone: string;
    address: string;
    roleId: number;
}

export enum Gender {
    Male = 1,
    Female = 2,
    Other = 0
}

export interface User {
    id: number;
    code: string;
    name: string;
    password?: string;
    gender: 1 | 2 | 0;
    birthday: string; // yyyy-MM-dd
    phone: string;
    address: string;
    age: number;
    roleId: number;
    createdBy?: number;
    creationDate?: string;
    modifiedBy?: number;
    modificationDate?: string;
}

export interface Role {
    id: number;
    code: string;
    name: string;
}

export interface UserQueryParams {
    queryName?: string;
    queryRole?: number;
    pageSize?: number;
    pageIndex?: number;
}

export interface UserListResponse {
    pageSize: number;
    totalPages: number;
    curPage: number;
    totalItems: number;
    users: User[];
}

export const formatGender = (gender: number): string => {
    switch (gender) {
        case 1:
            return '男';
        case 2:
            return '女';
        default:
            return '其他';
    }
};

