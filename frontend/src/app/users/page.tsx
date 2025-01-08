'use client';

import React, { useEffect, useRef, useState } from 'react';
import {
    Box,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField,
    Button,
    Select,
    MenuItem,
    FormControl,
    InputLabel,
    IconButton,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Typography,
    Breadcrumbs,
    Link,
    Alert,
    Snackbar,
    CircularProgress,
    TablePagination,
    Tooltip,
    Chip,
    Stack,
    Divider,
} from '@mui/material';
import {
    Visibility as ViewIcon,
    Edit as EditIcon,
    Delete as DeleteIcon,
    Add as AddIcon,
    Search as SearchIcon,
    Refresh as RefreshIcon,
} from '@mui/icons-material';
import { useRouter } from 'next/navigation';
import { User, Role, UserQueryParams, formatGender } from '@/types/user';
import { userService } from '@/services/userService';
import UserForm from "@/app/components/forms/UserForm";
import {checkAdminPermission, getCurrentId} from '@/utils/auth';

type DialogType = 'create' | 'edit' | 'view' | 'delete' | null;

export default function UserManagement() {
    useRouter();
    const [users, setUsers] = useState<User[]>([]);
    const [roles, setRoles] = useState<Role[]>([]);
    const [queryParams, setQueryParams] = useState<UserQueryParams>({
        pageSize: 10,
        pageIndex: 1,
    });
    const [totalItems, setTotalItems] = useState(0);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [dialogType, setDialogType] = useState<DialogType>(null);
    const [selectedUser, setSelectedUser] = useState<User | null>(null);
    const [formValues, setFormValues] = useState<Partial<User>>({});
    const [formValid, setFormValid] = useState(true);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);

    const userFormRef = useRef<{ validateForm: () => boolean }>(null);


    const fetchRoles = async () => {
        try {
            const data = await userService.getRoles();
            setRoles(data);
        } catch (err) {
            setError('获取角色列表失败');
            console.error('Error fetching roles:', err);
        }
    };

    const fetchUsers = async () => {
        try {
            setLoading(true);
            const data = await userService.getUsers(queryParams);
            setUsers(data.users);
            setTotalItems(data.totalItems);
        } catch (err) {
            setError('获取用户列表失败');
            console.error('Error fetching users:', err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchRoles();
    }, []);

    useEffect(() => {
        fetchUsers();
    }, [queryParams]);

    const handleQueryChange = (field: keyof UserQueryParams) => (
        event: React.ChangeEvent<HTMLInputElement> | { target: { value: unknown } }
    ) => {
        setQueryParams({
            ...queryParams,
            [field]: event.target.value,
            pageIndex: 1, // reset page index
        });
    };

    const handlePageChange = (event: unknown, newPage: number) => {
        setQueryParams({
            ...queryParams,
            pageIndex: newPage + 1, // Material-UI uses 0-based index
        });
    };

    const handlePageSizeChange = (
        event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
    ) => {
        const newPageSize = parseInt(event.target.value, 10);
        setQueryParams({
            ...queryParams,
            pageSize: newPageSize,
            pageIndex: 1, // Reset to first page when changing page size
        });
    };

    const handleOpenDialog = async (type: DialogType, user?: User) => {
        // auth check
        if ((type === 'create' || type === 'edit' || type === 'delete') && !checkAdminPermission(type)) {
            setError('您没有权限执行此操作');
            return;
        }
        setDialogType(type);
        setSelectedUser(user || null);

        // load user detail before view and edit
        if ((type === 'view' || type === 'edit') && user) {
            await loadUserDetails(user.id);
        } else {
            setFormValues(user || {});
        }
    };

    const handleCloseDialog = () => {
        setDialogType(null);
        setSelectedUser(null);
        setFormValues({});
        setFormValid(true);
    };

    const loadUserDetails = async (id: number) => {
        try {
            const user = await userService.getUser(id);
            // convert date to yyyy-MM-dd
            const formattedUser = {
                ...user,
                birthday: user.birthday ? user.birthday.split('T')[0] : '',
            };
            setSelectedUser(formattedUser);
            setFormValues(formattedUser);
        } catch (err) {
            setError('获取用户详情失败');
            console.error('Error loading user details:', err);
        }
    };

    const handleFormChange = (values: Partial<User>) => {
        setFormValues(values);
    };

    const handleCodeCheck = (exists: boolean) => {
        setFormValid(!exists);
    };

    const handleDelete = async (user: User) => {
        handleOpenDialog('delete', user);
    };

    const handleSave = async () => {
        if (!checkAdminPermission(dialogType === 'create' ? 'create' : 'edit')) {
            setError('您没有权限执行此操作');
            return;
        }

        // Validate form before saving
        const isValid = userFormRef.current?.validateForm();
        if (!isValid) {
            setError('请填写所有必填字段');
            return;
        }

        try {
            if (dialogType === 'create') {
                await userService.createUser(formValues as Omit<User, 'id'>);
                setSuccessMessage('创建用户成功');
            } else if (dialogType === 'edit' && selectedUser) {
                await userService.updateUser(selectedUser.id, formValues);
                setSuccessMessage('更新用户成功');
            }
            handleCloseDialog();
            fetchUsers();
        } catch (err) {
            setError(dialogType === 'create' ? '创建用户失败' : '更新用户失败');
            console.log('Error saving user:', err);
        }
    };

    const confirmDelete = async () => {
        if (!selectedUser) return;

        if (!checkAdminPermission('delete')) {
            setError('您没有权限执行此操作');
            return;
        }

        if (selectedUser?.id.toString() === getCurrentId()) {
            setError('不能删除自己');
            return;
        }

        try {
            await userService.deleteUser(selectedUser.id);
            setSuccessMessage('删除用户成功');
            handleCloseDialog();
            fetchUsers();
        } catch (err) {
            setError('删除用户失败');
            console.log('Error deleting user:', err);
        }
    };

    const getDialogTitle = () => {
        switch (dialogType) {
            case 'create':
                return '新建用户';
            case 'edit':
                return '编辑用户';
            case 'view':
                return '用户详情';
            case 'delete':
                return '确认删除';
            default:
                return '';
        }
    };

    const getStatusColor = (roleId: number) => {
        const role = roles.find(r => r.id === roleId);
        switch (role?.name) {
            case '系统管理员':
                return 'primary';
            case '经理':
                return 'secondary';
            default:
                return 'default';
        }
    };

    return (
        <Box>
            {/* Page Header */}
            <Box sx={{ mb: 4 }}>
                <Typography variant="h5" sx={{ mb: 1, fontWeight: 600 }}>
                    用户管理
                </Typography>
                <Breadcrumbs>
                    <Link color="inherit" href="/dashboard" sx={{
                        textDecoration: 'none',
                        '&:hover': { textDecoration: 'underline' }
                    }}>
                        首页
                    </Link>
                    <Typography color="text.primary">用户管理</Typography>
                </Breadcrumbs>
            </Box>

            {/* Query Form */}
            <Paper
                elevation={0}
                sx={{
                    p: 3,
                    mb: 3,
                    border: '1px solid',
                    borderColor: 'divider',
                    borderRadius: 2
                }}
            >
                <Stack spacing={3}>
                    <Typography variant="subtitle2" color="text.secondary">
                        筛选条件
                    </Typography>
                    <Stack
                        direction={{ xs: 'column', sm: 'row' }}
                        spacing={2}
                        alignItems="flex-end"
                    >
                        <TextField
                            label="用户名"
                            variant="outlined"
                            size="small"
                            value={queryParams.queryName || ''}
                            onChange={handleQueryChange('queryName')}
                            sx={{ minWidth: 200 }}
                        />

                        <FormControl size="small" sx={{ minWidth: 200 }}>
                            <InputLabel>用户角色</InputLabel>
                            <Select
                                value={queryParams.queryRole || ''}
                                onChange={handleQueryChange('queryRole')}
                                label="用户角色"
                            >
                                <MenuItem value="">
                                    <em>全部</em>
                                </MenuItem>
                                {roles.map((role) => (
                                    <MenuItem key={role.id} value={role.id}>
                                        {role.name}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>

                        <Box sx={{ display: 'flex', gap: 1 }}>
                            <Button
                                variant="contained"
                                startIcon={<SearchIcon />}
                                onClick={() => fetchUsers()}
                                disabled={loading}
                            >
                                查询
                            </Button>

                            <Button
                                variant="outlined"
                                startIcon={<RefreshIcon />}
                                onClick={() => {
                                    setQueryParams({ pageSize: 10, pageIndex: 1 });
                                }}
                            >
                                重置
                            </Button>
                        </Box>
                    </Stack>
                </Stack>
            </Paper>

            {/* User List */}
            <Paper
                elevation={0}
                sx={{
                    border: '1px solid',
                    borderColor: 'divider',
                    borderRadius: 2,
                    overflow: 'hidden'
                }}
            >
                <Box sx={{
                    p: 2,
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    borderBottom: '1px solid',
                    borderColor: 'divider'
                }}>
                    <Typography variant="subtitle2" color="text.secondary">
                        用户列表
                    </Typography>
                    <Button
                        variant="contained"
                        startIcon={<AddIcon />}
                        onClick={() => handleOpenDialog('create')}
                        sx={{
                            bgcolor: 'primary.main',
                            '&:hover': {
                                bgcolor: 'primary.dark',
                            }
                        }}
                    >
                        添加用户
                    </Button>
                </Box>

                <TableContainer>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>用户编码</TableCell>
                                <TableCell>用户名称</TableCell>
                                <TableCell>性别</TableCell>
                                <TableCell>年龄</TableCell>
                                <TableCell>电话</TableCell>
                                <TableCell>用户角色</TableCell>
                                <TableCell align="center">操作</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {loading ? (
                                <TableRow>
                                    <TableCell colSpan={7} align="center" sx={{ py: 8 }}>
                                        <CircularProgress size={40} />
                                    </TableCell>
                                </TableRow>
                            ) : users.length === 0 ? (
                                <TableRow>
                                    <TableCell colSpan={7} align="center" sx={{ py: 8 }}>
                                        <Typography color="text.secondary">
                                            暂无数据
                                        </Typography>
                                    </TableCell>
                                </TableRow>
                            ) : (
                                users.map((user) => (
                                    <TableRow key={user.id} hover>
                                        <TableCell>{user.code}</TableCell>
                                        <TableCell>
                                            <Typography fontWeight={500}>
                                                {user.name}
                                            </Typography>
                                        </TableCell>
                                        <TableCell>{formatGender(user.gender)}</TableCell>
                                        <TableCell>{user.age}</TableCell>
                                        <TableCell>{user.phone}</TableCell>
                                        <TableCell>
                                            <Chip
                                                label={roles.find(role => role.id === user.roleId)?.name}
                                                size="small"
                                                color={getStatusColor(user.roleId)}
                                            />
                                        </TableCell>
                                        <TableCell>
                                            <Stack
                                                direction="row"
                                                spacing={1}
                                                justifyContent="center"
                                            >
                                                <Tooltip title="查看详情">
                                                    <IconButton
                                                        size="small"
                                                        onClick={() => handleOpenDialog('view', user)}
                                                        sx={{
                                                            color: 'primary.light',
                                                            '&:hover': {
                                                                bgcolor: 'primary.lighter',
                                                                color: 'primary.main'
                                                            }
                                                        }}
                                                    >
                                                        <ViewIcon fontSize="small" />
                                                    </IconButton>
                                                </Tooltip>
                                                <Tooltip title="编辑用户">
                                                    <IconButton
                                                        size="small"
                                                        onClick={() => handleOpenDialog('edit', user)}
                                                        sx={{
                                                            color: 'grey.500',
                                                            '&:hover': {
                                                                bgcolor: 'grey.100',
                                                                color: 'grey.700'
                                                            }
                                                        }}
                                                    >
                                                        <EditIcon fontSize="small" />
                                                    </IconButton>
                                                </Tooltip>
                                                <Tooltip title="删除用户">
                                                    <IconButton
                                                        size="small"
                                                        onClick={() => handleDelete(user)}
                                                        sx={{
                                                            color: 'grey.500',
                                                            '&:hover': {
                                                                bgcolor: 'grey.100',
                                                                color: 'grey.700'
                                                            }
                                                        }}
                                                    >
                                                        <DeleteIcon fontSize="small" />
                                                    </IconButton>
                                                </Tooltip>
                                            </Stack>
                                        </TableCell>
                                    </TableRow>
                                ))
                            )}
                        </TableBody>
                    </Table>
                </TableContainer>

                <TablePagination
                    component="div"
                    count={totalItems}
                    page={(queryParams.pageIndex || 1) - 1}
                    rowsPerPage={queryParams.pageSize || 10}
                    onPageChange={handlePageChange}
                    onRowsPerPageChange={handlePageSizeChange}
                    rowsPerPageOptions={[5, 10, 20, 50]}
                    labelRowsPerPage="每页行数"
                    labelDisplayedRows={({from, to, count}) =>
                        `${from}-${to} 共 ${count} 条`
                    }
                    sx={{
                        borderTop: '1px solid',
                        borderColor: 'divider'
                    }}
                />
            </Paper>

            {/* Dialogs */}
            <Dialog
                open={dialogType === 'create' || dialogType === 'edit' || dialogType === 'view'}
                onClose={handleCloseDialog}
                maxWidth="md"
                fullWidth
                PaperProps={{
                    elevation: 0,
                    sx: {
                        borderRadius: 2,
                        border: '1px solid',
                        borderColor: 'divider'
                    }
                }}
            >
                <DialogTitle sx={{
                    borderBottom: '1px solid',
                    borderColor: 'divider',
                    pb: 2
                }}>
                    {getDialogTitle()}
                </DialogTitle>
                <DialogContent sx={{ p: 3 }}>
                    <UserForm
                        ref={userFormRef}
                        user={formValues}
                        roles={roles}
                        readOnly={dialogType === 'view'}
                        onChange={handleFormChange}
                        onCodeCheck={handleCodeCheck}
                    />
                </DialogContent>
                {dialogType !== 'view' && (
                    <DialogActions sx={{
                        borderTop: '1px solid',
                        borderColor: 'divider',
                        p: 2
                    }}>
                        <Button
                            onClick={handleCloseDialog}
                            variant="outlined"
                        >
                            取消
                        </Button>
                        <Button
                            onClick={handleSave}
                            variant="contained"
                            disabled={!formValid}
                        >
                            保存
                        </Button>
                    </DialogActions>
                )}
            </Dialog>

            <Dialog
                open={dialogType === 'delete'}
                onClose={handleCloseDialog}
                PaperProps={{
                    elevation: 0,
                    sx: {
                        borderRadius: 2,
                        border: '1px solid',
                        borderColor: 'divider'
                    }
                }}
            >
                <DialogTitle sx={{ pb: 2 }}>
                    {getDialogTitle()}
                </DialogTitle>
                <DialogContent sx={{ pb: 3 }}>
                    <Alert
                        severity="info"
                        sx={{
                            mb: 2,
                            '& .MuiAlert-icon': {
                                color: 'primary.main'
                            }
                        }}
                    >
                        此操作将永久删除该用户，是否继续？
                    </Alert>
                    <Typography>
                        用户名称：{selectedUser?.name}
                    </Typography>
                </DialogContent>
                <DialogActions sx={{ p: 2, pt: 0 }}>
                    <Button
                        onClick={handleCloseDialog}
                        variant="outlined"
                    >
                        取消
                    </Button>
                    <Button
                        onClick={confirmDelete}
                        variant="contained"
                        color="primary"
                    >
                        确认删除
                    </Button>
                </DialogActions>
            </Dialog>

            <Snackbar
                open={!!error}
                autoHideDuration={6000}
                onClose={() => setError(null)}
                anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
            >
                <Alert
                    severity="error"
                    variant="filled"
                    onClose={() => setError(null)}
                >
                    {error}
                </Alert>
            </Snackbar>

            <Snackbar
                open={!!successMessage}
                autoHideDuration={3000}
                onClose={() => setSuccessMessage(null)}
                anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
            >
                <Alert
                    severity="success"
                    variant="filled"
                    onClose={() => setSuccessMessage(null)}
                >
                    {successMessage}
                </Alert>
            </Snackbar>
        </Box>
    );
}

