'use client';

import React, {useEffect, useRef, useState} from 'react';
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
} from '@mui/material';
import {
    Visibility as ViewIcon,
    Edit as EditIcon,
    Delete as DeleteIcon,
    Add as AddIcon,
} from '@mui/icons-material';
import {useRouter} from 'next/navigation';
import {User, Role, UserQueryParams, formatGender} from '@/types/user';
import {userService} from '@/services/userService';
import UserForm from "@/app/components/forms/UserForm";
import {checkPermission} from '@/utils/auth';

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
        if ((type === 'create' || type === 'edit' || type === 'delete') && !checkPermission(type)) {
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
        if (!checkPermission(dialogType === 'create' ? 'create' : 'edit')) {
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
            console.error('Error saving user:', err);
        }
    };

    const confirmDelete = async () => {
        if (!selectedUser) return;

        if (!checkPermission('delete')) {
            setError('您没有权限执行此操作');
            return;
        }

        try {
            await userService.deleteUser(selectedUser.id);
            setSuccessMessage('删除用户成功');
            handleCloseDialog();
            fetchUsers();
        } catch (err) {
            setError('删除用户失败');
            console.error('Error deleting user:', err);
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

    return (
        <Box sx={{p: 3}}>
            {/* breadcrumbs nav */}
            <Breadcrumbs sx={{mb: 3}}>
                <Link color="inherit" href="/dashboard">
                    首页
                </Link>
                <Typography color="text.primary">用户管理</Typography>
            </Breadcrumbs>

            {/* query form */}
            <Paper sx={{p: 2, mb: 3}}>
                <Box sx={{display: 'flex', gap: 2, alignItems: 'flex-end'}}>
                    <TextField
                        label="用户名"
                        variant="outlined"
                        size="small"
                        value={queryParams.queryName || ''}
                        onChange={handleQueryChange('queryName')}
                    />

                    <FormControl size="small" sx={{minWidth: 120}}>
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

                    <Button
                        variant="contained"
                        onClick={() => fetchUsers()}
                        disabled={loading}
                    >
                        {loading ? <CircularProgress size={24}/> : '查询'}
                    </Button>

                    <Button
                        variant="outlined"
                        startIcon={<AddIcon/>}
                        onClick={() => handleOpenDialog('create')}
                    >
                        添加用户
                    </Button>
                </Box>
            </Paper>

            {/* user list */}
            <Paper>
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
                                <TableCell>操作</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {loading ? (
                                <TableRow>
                                    <TableCell colSpan={7} align="center">
                                        <CircularProgress/>
                                    </TableCell>
                                </TableRow>
                            ) : users.length === 0 ? (
                                <TableRow>
                                    <TableCell colSpan={7} align="center">
                                        暂无数据
                                    </TableCell>
                                </TableRow>
                            ) : (
                                users.map((user) => (
                                    <TableRow key={user.id}>
                                        <TableCell>{user.code}</TableCell>
                                        <TableCell>{user.name}</TableCell>
                                        <TableCell>{formatGender(user.gender)}</TableCell>
                                        <TableCell>{user.age}</TableCell>
                                        <TableCell>{user.phone}</TableCell>
                                        <TableCell>
                                            {roles.find(role => role.id === user.roleId)?.name}
                                        </TableCell>
                                        <TableCell>
                                            <IconButton
                                                size="small"
                                                onClick={() => handleOpenDialog('view', user)}
                                            >
                                                <ViewIcon/>
                                            </IconButton>
                                            <IconButton
                                                size="small"
                                                onClick={() => handleOpenDialog('edit', user)}
                                            >
                                                <EditIcon/>
                                            </IconButton>
                                            <IconButton
                                                size="small"
                                                onClick={() => handleDelete(user)}
                                            >
                                                <DeleteIcon/>
                                            </IconButton>
                                        </TableCell>
                                    </TableRow>
                                ))
                            )}
                        </TableBody>
                    </Table>
                </TableContainer>

                {/* pager */}
                <TablePagination
                    component="div"
                    count={totalItems}
                    page={(queryParams.pageIndex || 1) - 1} // Convert to 0-based index for MUI
                    rowsPerPage={queryParams.pageSize || 10}
                    onPageChange={handlePageChange}
                    onRowsPerPageChange={handlePageSizeChange}
                    rowsPerPageOptions={[5, 10, 20, 50]}
                    labelRowsPerPage="每页行数"
                    labelDisplayedRows={({from, to, count}) =>
                        `${from}-${to} 共 ${count} 条`
                    }
                />
            </Paper>

            {/* user form dialog */}
            <Dialog
                open={dialogType === 'create' || dialogType === 'edit' || dialogType === 'view'}
                onClose={handleCloseDialog}
                maxWidth="md"
                fullWidth
            >
                <DialogTitle>{getDialogTitle()}</DialogTitle>
                <DialogContent>
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
                    <DialogActions>
                        <Button onClick={handleCloseDialog}>取消</Button>
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

            {/* delete-confirmation dialog */}
            <Dialog
                open={dialogType === 'delete'}
                onClose={handleCloseDialog}
            >
                <DialogTitle>{getDialogTitle()}</DialogTitle>
                <DialogContent>
                    <Typography>
                        确定要删除用户 {selectedUser?.name} 吗？
                    </Typography>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog}>取消</Button>
                    <Button onClick={confirmDelete} color="error">
                        删除
                    </Button>
                </DialogActions>
            </Dialog>

            {/* error message */}
            <Snackbar
                open={!!error}
                autoHideDuration={6000}
                onClose={() => setError(null)}
            >
                <Alert severity="error" onClose={() => setError(null)}>
                    {error}
                </Alert>
            </Snackbar>

            {/* success message */}
            <Snackbar
                open={!!successMessage}
                autoHideDuration={3000}
                onClose={() => setSuccessMessage(null)}
            >
                <Alert severity="success" onClose={() => setSuccessMessage(null)}>
                    {successMessage}
                </Alert>
            </Snackbar>
        </Box>
    );
}

