export const ADMIN_ROLE = 'SMBMS_ADMIN';

export const isAdmin = (): boolean => {
    if (typeof window === 'undefined') return false;
    const userRoleCode = localStorage.getItem('userRoleCode');
    return userRoleCode === ADMIN_ROLE;
};

export const checkPermission = (action: 'create' | 'edit' | 'delete'): boolean => {
    return isAdmin();
};
