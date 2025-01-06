export interface ErrorResponse {
    status: number;
    message: string;
}

export interface LoginRequest {
    username: string;
    password: string;
}

export interface LoginResponse {
    token: string;
    userName: string;
    userId: number;
    userCode: string;
    userRoleCode: string;
}

export interface PasswordUpdateRequest {
    newPassword: string;
}

