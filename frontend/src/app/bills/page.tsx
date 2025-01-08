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
    FormControl,
    InputLabel,
    Select,
    MenuItem,
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
    Chip,
} from '@mui/material';
import {
    Visibility as ViewIcon,
    Edit as EditIcon,
    Delete as DeleteIcon,
    Add as AddIcon,
    Search as SearchIcon,
    Refresh as RefreshIcon,
    Store as StoreIcon,
} from '@mui/icons-material';
import {useRouter} from 'next/navigation';
import {Bill, BillQueryParams, paymentStatus, Provider} from '@/types';
import {billService} from '@/services/billService';
import BillForm from "@/app/components/forms/BillForm";
import {checkManagerPermission} from '@/utils/auth';

type DialogType = 'create' | 'edit' | 'view' | 'delete' | null;

export default function BillManagement() {
    useRouter();
    const [bills, setBills] = useState<Bill[]>([]);
    const [providers, setProviders] = useState<Provider[]>([]);
    const [queryParams, setQueryParams] = useState<BillQueryParams>({
        pageSize: 10,
        pageIndex: 1,
    });
    const [totalItems, setTotalItems] = useState(0);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [dialogType, setDialogType] = useState<DialogType>(null);
    const [selectedBill, setSelectedBill] = useState<Bill | null>(null);
    const [formValues, setFormValues] = useState<Partial<Bill>>({});
    const [formValid, setFormValid] = useState(true);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);

    const billFormRef = useRef<{ validateForm: () => boolean }>(null);

    const fetchProviders = async () => {
        try {
            const data = await billService.getListOfProviders();
            setProviders(data);
        } catch (err) {
            setError('获取供应商列表失败');
            console.log('Error fetching providers:', err);
        }
    };

    const fetchBills = async () => {
        try {
            setLoading(true);
            const data = await billService.getBills(queryParams);
            setBills(data.bills);
            setTotalItems(data.totalItems);
        } catch (err) {
            setError('获取订单列表失败');
            console.error('Error fetching bills:', err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchProviders();
    }, []);

    useEffect(() => {
        fetchBills();
    }, [queryParams]);

    const handleQueryChange = (field: keyof BillQueryParams) => (
        event: React.ChangeEvent<HTMLInputElement> | { target: { value: unknown } }
    ) => {
        setQueryParams({
            ...queryParams,
            [field]: event.target.value,
            pageIndex: 1,
        });
    };

    const handlePageChange = (event: unknown, newPage: number) => {
        setQueryParams({
            ...queryParams,
            pageIndex: newPage + 1,
        });
    };

    const handlePageSizeChange = (
        event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
    ) => {
        setQueryParams({
            ...queryParams,
            pageSize: parseInt(event.target.value, 10),
            pageIndex: 1,
        });
    };

    const handleOpenDialog = async (type: DialogType, bill?: Bill) => {
        if ((type === 'create' || type === 'edit' || type === 'delete') && !checkManagerPermission(type)) {
            setError('您没有权限执行此操作');
            return;
        }
        setDialogType(type);
        setSelectedBill(bill || null);

        if ((type === 'view' || type === 'edit') && bill) {
            await loadBillDetails(bill.id);
        } else {
            setFormValues(bill || {});
        }
    };

    const handleCloseDialog = () => {
        setDialogType(null);
        setSelectedBill(null);
        setFormValues({});
        setFormValid(true);
    };

    const loadBillDetails = async (id: number) => {
        try {
            const bill = await billService.getBillById(id);
            setSelectedBill(bill);
            setFormValues(bill);
        } catch (err) {
            setError('获取订单详情失败');
            console.error('Error loading bill details:', err);
        }
    };

    const handleFormChange = (values: Partial<Bill>) => {
        setFormValues(values);
    };

    const handleCodeCheck = (exists: boolean) => {
        setFormValid(!exists);
    };

    const handleDelete = async (bill: Bill) => {
        handleOpenDialog('delete', bill);
    };

    const handleSave = async () => {
        if (!checkManagerPermission(dialogType === 'create' ? 'create' : 'edit')) {
            setError('您没有权限执行此操作');
            return;
        }

        // Validate form before saving
        const isValid = billFormRef.current?.validateForm();
        if (!isValid) {
            setError('请填写所有必填字段');
            return;
        }

        try {
            if (dialogType === 'create') {
                console.log(formValues)
                await billService.createBill(formValues as Omit<Bill, 'id'>);
                setSuccessMessage('创建订单成功');
            } else if (dialogType === 'edit' && selectedBill) {
                await billService.updateBill(selectedBill.id, formValues);
                setSuccessMessage('更新订单成功');
            }
            handleCloseDialog();
            fetchBills();
        } catch (err) {
            setError(dialogType === 'create' ? '创建订单失败' : '更新订单失败');
            console.log('Error saving bill:', err);
        }
    };

    const confirmDelete = async () => {
        if (!selectedBill) return;

        if (!checkManagerPermission('delete')) {
            setError('您没有权限执行此操作');
            return;
        }

        try {
            await billService.deleteBill(selectedBill.id);
            setSuccessMessage('删除订单成功');
            handleCloseDialog();
            fetchBills();
        } catch (err) {
            setError('删除订单失败');
            console.log('Error deleting bill:', err);
        }
    };

    const getDialogTitle = () => {
        switch (dialogType) {
            case 'create':
                return '新建订单';
            case 'edit':
                return '编辑订单';
            case 'view':
                return '订单详情';
            case 'delete':
                return '确认删除';
            default:
                return '';
        }
    };

    const formatPrice = (price: number) => {
        return new Intl.NumberFormat('zh-CN', {
            style: 'currency',
            currency: 'CNY'
        }).format(price);
    };

    const getPaymentStatusChip = (isPaid: number) => {
        return (
            <Chip
                label={isPaid === paymentStatus.PAID ? '已支付' : '未支付'}
                size="small"
                color={isPaid === paymentStatus.PAID ? 'success' : 'default'}
                sx={{
                    '& .MuiChip-label': {
                        px: 2
                    }
                }}
            />
        );
    };

    return (
        <Box>
            {/* Page Header */}
            <Box sx={{mb: 4}}>
                <Typography variant="h5" sx={{mb: 1, fontWeight: 600}}>
                    订单管理
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
                    <Typography color="text.primary">订单管理</Typography>
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
                        direction={{xs: 'column', md: 'row'}}
                        spacing={2}
                        alignItems="flex-end"
                        flexWrap="wrap"
                        useFlexGap
                    >
                        <TextField
                            label="订单编码"
                            variant="outlined"
                            size="small"
                            value={queryParams.queryCode || ''}
                            onChange={handleQueryChange('queryCode')}
                            sx={{minWidth: 200}}
                        />
                        <TextField
                            label="产品名称"
                            variant="outlined"
                            size="small"
                            value={queryParams.queryProductName || ''}
                            onChange={handleQueryChange('queryProductName')}
                            sx={{minWidth: 200}}
                        />
                        <TextField
                            label="供应商名称"
                            variant="outlined"
                            size="small"
                            value={queryParams.queryProviderName || ''}
                            onChange={handleQueryChange('queryProviderName')}
                            sx={{minWidth: 200}}
                        />
                        <FormControl size="small" sx={{minWidth: 200}}>
                            <InputLabel>支付状态</InputLabel>
                            <Select
                                value={queryParams.queryIsPaid || ''}
                                onChange={handleQueryChange('queryIsPaid')}
                                label="支付状态"
                            >
                                <MenuItem value="">
                                    <em>全部</em>
                                </MenuItem>
                                <MenuItem value={paymentStatus.PENDING}>未支付</MenuItem>
                                <MenuItem value={paymentStatus.PAID}>已支付</MenuItem>
                            </Select>
                        </FormControl>

                        <Box sx={{display: 'flex', gap: 1}}>
                            <Button
                                variant="contained"
                                startIcon={<SearchIcon/>}
                                onClick={() => fetchBills()}
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

            {/* Bill List */}
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
                        订单列表
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
                        添加订单
                    </Button>
                </Box>

                <TableContainer>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>订单编码</TableCell>
                                <TableCell>产品信息</TableCell>
                                <TableCell align="right">金额</TableCell>
                                <TableCell>供应商</TableCell>
                                <TableCell align="center">支付状态</TableCell>
                                <TableCell align="center">操作</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {loading ? (
                                <TableRow>
                                    <TableCell colSpan={6} align="center" sx={{py: 8}}>
                                        <CircularProgress size={40}/>
                                    </TableCell>
                                </TableRow>
                            ) : bills.length === 0 ? (
                                <TableRow>
                                    <TableCell colSpan={6} align="center" sx={{py: 8}}>
                                        <Typography color="text.secondary">
                                            暂无数据
                                        </Typography>
                                    </TableCell>
                                </TableRow>
                            ) : (
                                bills.map((bill) => (
                                    <TableRow key={bill.id} hover>
                                        <TableCell>
                                            <Typography fontWeight={500}>
                                                {bill.code}
                                            </Typography>
                                        </TableCell>
                                        <TableCell>
                                            <Stack spacing={1}>
                                                <Typography fontWeight={500}>
                                                    {bill.productName}
                                                </Typography>
                                                <Typography
                                                    variant="body2"
                                                    color="text.secondary"
                                                >
                                                    数量: {bill.productCount} {bill.productUnit}
                                                </Typography>
                                            </Stack>
                                        </TableCell>
                                        <TableCell align="right">
                                            <Typography
                                                fontWeight={500}
                                                color="primary.main"
                                                sx={{
                                                    display: 'flex',
                                                    alignItems: 'center',
                                                    justifyContent: 'flex-end',
                                                    gap: 0.5
                                                }}
                                            >
                                                {formatPrice(bill.totalPrice)}
                                            </Typography>
                                        </TableCell>
                                        <TableCell>
                                            <Stack direction="row" spacing={1} alignItems="center">
                                                <StoreIcon
                                                    fontSize="small"
                                                    sx={{color: 'primary.light'}}
                                                />
                                                <Typography>
                                                    {providers.find(p => p.id === bill.providerId)?.name}
                                                </Typography>
                                            </Stack>
                                        </TableCell>
                                        <TableCell align="center">
                                            {getPaymentStatusChip(bill.isPaid)}
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
                                                        onClick={() => handleOpenDialog('view', bill)}
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
                                                <Tooltip title="编辑订单">
                                                    <IconButton
                                                        size="small"
                                                        onClick={() => handleOpenDialog('edit', bill)}
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
                                                <Tooltip title="删除订单">
                                                    <IconButton
                                                        size="small"
                                                        onClick={() => handleDelete(bill)}
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
                    <BillForm
                        ref={billFormRef}
                        bill={formValues}
                        providers={providers}
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
                        此操作将永久删除该订单，是否继续？
                    </Alert>
                    <Typography>
                        订单编码：{selectedBill?.code}
                    </Typography>
                    <Typography color="text.secondary" sx={{mt: 1}}>
                        产品：{selectedBill?.productName}
                    </Typography>
                    <Typography
                        sx={{
                            mt: 1,
                            color: 'primary.main',
                            fontWeight: 500
                        }}
                    >
                        总金额：{selectedBill?.totalPrice && formatPrice(selectedBill.totalPrice)}
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
