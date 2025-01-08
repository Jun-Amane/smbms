export const ADMIN_ROLE = 'SMBMS_ADMIN';
export const MANAGER_ROLE = 'SMBMS_MANAGER';

export const isAdmin = (): boolean => {
    if (typeof window === 'undefined') return false;
    const userRoleCode = localStorage.getItem('userRoleCode');
    return userRoleCode === ADMIN_ROLE;
};

export const isManager = (): boolean => {
    if (typeof window === 'undefined') return false;
    const userRoleCode = localStorage.getItem('userRoleCode');
    return userRoleCode === MANAGER_ROLE;
};

export const checkAdminPermission = (action: 'create' | 'edit' | 'delete'): boolean => {
    return isAdmin();
};

export const checkManagerPermission = (action: 'create' | 'edit' | 'delete'): boolean => {
    return isAdmin() || isManager();
};

export const getCurrentId = () => localStorage.getItem('userId');
