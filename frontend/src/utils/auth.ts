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

export const checkAdminPermission = (): boolean => {
    return isAdmin();
};

export const checkManagerPermission = (): boolean => {
    return isAdmin() || isManager();
};

export const getCurrentId = () => localStorage.getItem('userId');
