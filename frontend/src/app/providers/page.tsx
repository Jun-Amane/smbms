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
import { useRouter } from 'next/navigation';
import { Provider, ProviderQueryParams } from '@/types';
import { providerService } from '@/services/providerService';
import ProviderForm from "@/app/components/forms/ProviderForm";
import { checkPermission } from '@/utils/auth';

type DialogType = 'create' | 'edit' | 'view' | 'delete' | null;

export default function ProviderManagement() {
    useRouter();
    const [providers, setProviders] = useState<Provider[]>([]);
    const [queryParams, setQueryParams] = useState<ProviderQueryParams>({
        pageSize: 10,
        pageIndex: 1,
    });
    const [totalItems, setTotalItems] = useState(0);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [dialogType, setDialogType] = useState<DialogType>(null);
    const [selectedProvider, setSelectedProvider] = useState<Provider | null>(null);
    const [formValues, setFormValues] = useState<Partial<Provider>>({});
    const [formValid, setFormValid] = useState(true);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);

    const providerFormRef = useRef<{ validateForm: () => boolean }>(null);

    const fetchProviders = async () => {
        try {
            setLoading(true);
            const data = await providerService.getProviders(queryParams);
            setProviders(data.providers);
            setTotalItems(data.totalItems);
        } catch (err) {
            setError('获取供应商列表失败');
            console.error('Error fetching providers:', err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchProviders();
    }, [queryParams]);

    const handleQueryChange = (field: keyof ProviderQueryParams) => (
        event: React.ChangeEvent<HTMLInputElement>
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

    const handleOpenDialog = async (type: DialogType, provider?: Provider) => {
        if ((type === 'create' || type === 'edit' || type === 'delete') && !checkPermission(type)) {
            setError('您没有权限执行此操作');
            return;
        }
        setDialogType(type);
        setSelectedProvider(provider || null);

        if ((type === 'view' || type === 'edit') && provider) {
            await loadProviderDetails(provider.id);
        } else {
            setFormValues(provider || {});
        }
    };

    const handleCloseDialog = () => {
        setDialogType(null);
        setSelectedProvider(null);
        setFormValues({});
        setFormValid(true);
    };

    const loadProviderDetails = async (id: number) => {
        try {
            const provider = await providerService.getProvider(id);
            setSelectedProvider(provider);
            setFormValues(provider);
        } catch (err) {
            setError('获取供应商详情失败');
            console.error('Error loading provider details:', err);
        }
    };

    const handleFormChange = (values: Partial<Provider>) => {
        setFormValues(values);
    };

    const handleCodeCheck = (exists: boolean) => {
        setFormValid(!exists);
    };

    const handleDelete = async (provider: Provider) => {
        handleOpenDialog('delete', provider);
    };

    const handleSave = async () => {
        if (!checkPermission(dialogType === 'create' ? 'create' : 'edit')) {
            setError('您没有权限执行此操作');
            return;
        }

        // Validate form before saving
        const isValid = providerFormRef.current?.validateForm();
        if (!isValid) {
            setError('请填写所有必填字段');
            return;
        }

        try {
            if (dialogType === 'create') {
                await providerService.createProvider(formValues as Omit<Provider, 'id'>);
                setSuccessMessage('创建供应商成功');
            } else if (dialogType === 'edit' && selectedProvider) {
                await providerService.updateProvider(selectedProvider.id, formValues);
                setSuccessMessage('更新供应商成功');
            }
            handleCloseDialog();
            fetchProviders();
        } catch (err) {
            setError(dialogType === 'create' ? '创建供应商失败' : '更新供应商失败');
            console.error('Error saving provider:', err);
        }
    };

    const confirmDelete = async () => {
        if (!selectedProvider) return;

        if (!checkPermission('delete')) {
            setError('您没有权限执行此操作');
            return;
        }

        try {
            await providerService.deleteProvider(selectedProvider.id);
            setSuccessMessage('删除供应商成功');
            handleCloseDialog();
            fetchProviders();
        } catch (err) {
            setError('删除供应商失败');
            console.error('Error deleting provider:', err);
        }
    };

    const getDialogTitle = () => {
        switch (dialogType) {
            case 'create':
                return '新建供应商';
            case 'edit':
                return '编辑供应商';
            case 'view':
                return '供应商详情';
            case 'delete':
                return '确认删除';
            default:
                return '';
        }
    };

    return (
        <Box sx={{ p: 3 }}>
            {/* breadcrumbs nav */}
            <Breadcrumbs sx={{ mb: 3 }}>
                <Link color="inherit" href="/dashboard">
                    首页
                </Link>
                <Typography color="text.primary">供应商管理</Typography>
            </Breadcrumbs>

            {/* query form */}
            <Paper sx={{ p: 2, mb: 3 }}>
                <Box sx={{ display: 'flex', gap: 2, alignItems: 'flex-end' }}>
                    <TextField
                        label="供应商编码"
                        variant="outlined"
                        size="small"
                        value={queryParams.queryCode || ''}
                        onChange={handleQueryChange('queryCode')}
                    />
                    <TextField
                        label="供应商名称"
                        variant="outlined"
                        size="small"
                        value={queryParams.queryName || ''}
                        onChange={handleQueryChange('queryName')}
                    />
                    <Button
                        variant="contained"
                        onClick={() => fetchProviders()}
                        disabled={loading}
                    >
                        {loading ? <CircularProgress size={24} /> : '查询'}
                    </Button>

                    <Button
                        variant="outlined"
                        startIcon={<AddIcon />}
                        onClick={() => handleOpenDialog('create')}
                    >
                        添加供应商
                    </Button>
                </Box>
            </Paper>

            {/* provider list */}
            <Paper>
                <TableContainer>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>供应商编码</TableCell>
                                <TableCell>供应商名称</TableCell>
                                <TableCell>联系人</TableCell>
                                <TableCell>电话</TableCell>
                                <TableCell>传真</TableCell>
                                <TableCell>地址</TableCell>
                                <TableCell>操作</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {loading ? (
                                <TableRow>
                                    <TableCell colSpan={7} align="center">
                                        <CircularProgress />
                                    </TableCell>
                                </TableRow>
                            ) : providers.length === 0 ? (
                                <TableRow>
                                    <TableCell colSpan={7} align="center">
                                        暂无数据
                                    </TableCell>
                                </TableRow>
                            ) : (
                                providers.map((provider) => (
                                    <TableRow key={provider.id}>
                                        <TableCell>{provider.code}</TableCell>
                                        <TableCell>{provider.name}</TableCell>
                                        <TableCell>{provider.contact}</TableCell>
                                        <TableCell>{provider.phone}</TableCell>
                                        <TableCell>{provider.fax}</TableCell>
                                        <TableCell>{provider.address}</TableCell>
                                        <TableCell>
                                            <IconButton
                                                size="small"
                                                onClick={() => handleOpenDialog('view', provider)}
                                            >
                                                <ViewIcon />
                                            </IconButton>
                                            <IconButton
                                                size="small"
                                                onClick={() => handleOpenDialog('edit', provider)}
                                            >
                                                <EditIcon />
                                            </IconButton>
                                            <IconButton
                                                size="small"
                                                onClick={() => handleDelete(provider)}
                                            >
                                                <DeleteIcon />
                                            </IconButton>
                                        </TableCell>
                                    </TableRow>
                                ))
                            )}
                        </TableBody>
                    </Table>
                </TableContainer>

                {/* pagination */}
                <TablePagination
                    component="div"
                    count={totalItems}
                    page={(queryParams.pageIndex || 1) - 1}
                    rowsPerPage={queryParams.pageSize || 10}
                    onPageChange={handlePageChange}
                    onRowsPerPageChange={handlePageSizeChange}
                    rowsPerPageOptions={[5, 10, 20, 50]}
                    labelRowsPerPage="每页行数"
                    labelDisplayedRows={({ from, to, count }) =>
                        `${from}-${to} 共 ${count} 条`
                    }
                />
            </Paper>

            {/* provider form dialog */}
            <Dialog
                open={dialogType === 'create' || dialogType === 'edit' || dialogType === 'view'}
                onClose={handleCloseDialog}
                maxWidth="md"
                fullWidth
            >
                <DialogTitle>{getDialogTitle()}</DialogTitle>
                <DialogContent>
                    <ProviderForm
                        ref={providerFormRef}
                        provider={formValues}
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

            {/* delete confirmation dialog */}
            <Dialog
                open={dialogType === 'delete'}
                onClose={handleCloseDialog}
            >
                <DialogTitle>{getDialogTitle()}</DialogTitle>
                <DialogContent>
                    <Typography>
                        确定要删除供应商 {selectedProvider?.name} 吗？
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

