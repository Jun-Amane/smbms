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
    Tooltip,
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
    LocationOn as LocationIcon,
    Phone as PhoneIcon,
    Person as PersonIcon,
} from '@mui/icons-material';
import { useRouter } from 'next/navigation';
import { Provider, ProviderQueryParams } from '@/types';
import { providerService } from '@/services/providerService';
import ProviderForm from "@/app/components/forms/ProviderForm";
import { checkPermission } from '@/utils/auth';

type DialogType = 'create' | 'edit' | 'view' | 'delete' | null;

export default function ProviderManagement() {
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
        <Box>
            {/* Page Header */}
            <Box sx={{mb: 4}}>
                <Typography variant="h5" sx={{mb: 1, fontWeight: 600}}>
                    供应商管理
                </Typography>
                <Breadcrumbs>
                    <Link
                        color="inherit"
                        href="/dashboard"
                        sx={{
                            textDecoration: 'none',
                            '&:hover': {textDecoration: 'underline'}
                        }}
                    >
                        首页
                    </Link>
                    <Typography color="text.primary">供应商管理</Typography>
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
                        direction={{xs: 'column', sm: 'row'}}
                        spacing={2}
                        alignItems="flex-end"
                    >
                        <TextField
                            label="供应商编码"
                            variant="outlined"
                            size="small"
                            value={queryParams.queryCode || ''}
                            onChange={handleQueryChange('queryCode')}
                            sx={{minWidth: 200}}
                        />
                        <TextField
                            label="供应商名称"
                            variant="outlined"
                            size="small"
                            value={queryParams.queryName || ''}
                            onChange={handleQueryChange('queryName')}
                            sx={{minWidth: 200}}
                        />

                        <Box sx={{display: 'flex', gap: 1}}>
                            <Button
                                variant="contained"
                                startIcon={<SearchIcon/>}
                                onClick={() => fetchProviders()}
                                disabled={loading}
                            >
                                查询
                            </Button>

                            <Button
                                variant="outlined"
                                startIcon={<RefreshIcon/>}
                                onClick={() => {
                                    setQueryParams({pageSize: 10, pageIndex: 1});
                                }}
                            >
                                重置
                            </Button>
                        </Box>
                    </Stack>
                </Stack>
            </Paper>

            {/* Provider List */}
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
                        供应商列表
                    </Typography>
                    <Button
                        variant="contained"
                        startIcon={<AddIcon/>}
                        onClick={() => handleOpenDialog('create')}
                        sx={{
                            bgcolor: 'primary.main',
                            '&:hover': {
                                bgcolor: 'primary.dark',
                            }
                        }}
                    >
                        添加供应商
                    </Button>
                </Box>

                <TableContainer>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>供应商编码</TableCell>
                                <TableCell>供应商名称</TableCell>
                                <TableCell>联系人</TableCell>
                                <TableCell>联系方式</TableCell>
                                <TableCell>地址</TableCell>
                                <TableCell align="center">操作</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {loading ? (
                                <TableRow>
                                    <TableCell colSpan={7} align="center" sx={{py: 8}}>
                                        <CircularProgress size={40}/>
                                    </TableCell>
                                </TableRow>
                            ) : providers.length === 0 ? (
                                <TableRow>
                                    <TableCell colSpan={7} align="center" sx={{py: 8}}>
                                        <Typography color="text.secondary">
                                            暂无数据
                                        </Typography>
                                    </TableCell>
                                </TableRow>
                            ) : (
                                providers.map((provider) => (
                                    <TableRow key={provider.id} hover>
                                        <TableCell>{provider.code}</TableCell>
                                        <TableCell>
                                            <Typography fontWeight={500}>
                                                {provider.name}
                                            </Typography>
                                        </TableCell>
                                        <TableCell>
                                            <Stack direction="row" spacing={1} alignItems="center">
                                                <PersonIcon
                                                    fontSize="small"
                                                    sx={{color: 'primary.light'}}
                                                />
                                                {provider.contact}
                                            </Stack>
                                        </TableCell>
                                        <TableCell>
                                            <Stack spacing={1}>
                                                <Stack direction="row" spacing={1} alignItems="center">
                                                    <PhoneIcon
                                                        fontSize="small"
                                                        sx={{color: 'primary.light'}}
                                                    />
                                                    {provider.phone}
                                                </Stack>
                                                {provider.fax && (
                                                    <Typography
                                                        variant="body2"
                                                        color="text.secondary"
                                                    >
                                                        传真: {provider.fax}
                                                    </Typography>
                                                )}
                                            </Stack>
                                        </TableCell>
                                        <TableCell>
                                            <Stack direction="row" spacing={1} alignItems="center">
                                                <LocationIcon
                                                    fontSize="small"
                                                    sx={{color: 'primary.light'}}
                                                />
                                                <Typography
                                                    sx={{
                                                        maxWidth: 200,
                                                        overflow: 'hidden',
                                                        textOverflow: 'ellipsis',
                                                        whiteSpace: 'nowrap'
                                                    }}
                                                >
                                                    {provider.address}
                                                </Typography>
                                            </Stack>
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
                                                        onClick={() => handleOpenDialog('view', provider)}
                                                        sx={{
                                                            color: 'primary.light',
                                                            '&:hover': {
                                                                bgcolor: 'primary.lighter',
                                                                color: 'primary.main'
                                                            }
                                                        }}
                                                    >
                                                        <ViewIcon fontSize="small"/>
                                                    </IconButton>
                                                </Tooltip>
                                                <Tooltip title="编辑供应商">
                                                    <IconButton
                                                        size="small"
                                                        onClick={() => handleOpenDialog('edit', provider)}
                                                        sx={{
                                                            color: 'grey.500',
                                                            '&:hover': {
                                                                bgcolor: 'grey.100',
                                                                color: 'grey.700'
                                                            }
                                                        }}
                                                    >
                                                        <EditIcon fontSize="small"/>
                                                    </IconButton>
                                                </Tooltip>
                                                <Tooltip title="删除供应商">
                                                    <IconButton
                                                        size="small"
                                                        onClick={() => handleDelete(provider)}
                                                        sx={{
                                                            color: 'grey.500',
                                                            '&:hover': {
                                                                bgcolor: 'grey.100',
                                                                color: 'grey.700'
                                                            }
                                                        }}
                                                    >
                                                        <DeleteIcon fontSize="small"/>
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
                <DialogContent sx={{p: 3}}>
                    <ProviderForm
                        ref={providerFormRef}
                        provider={formValues}
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
                <DialogTitle sx={{pb: 2}}>
                    {getDialogTitle()}
                </DialogTitle>
                <DialogContent sx={{pb: 3}}>
                    <Alert
                        severity="info"
                        sx={{
                            mb: 2,
                            '& .MuiAlert-icon': {
                                color: 'primary.main'
                            }
                        }}
                    >
                        此操作将永久删除该供应商，是否继续？
                    </Alert>
                    <Typography>
                        供应商名称：{selectedProvider?.name}
                    </Typography>
                </DialogContent>
                <DialogActions sx={{p: 2, pt: 0}}>
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
                anchorOrigin={{vertical: 'top', horizontal: 'right'}}
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