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
} from '@mui/material';
import {
    Visibility as ViewIcon,
    Edit as EditIcon,
    Delete as DeleteIcon,
    Add as AddIcon,
} from '@mui/icons-material';
import { useRouter } from 'next/navigation';
import { Bill, BillQueryParams, Provider } from '@/types';
import { billService } from '@/services/billService';
import BillForm from "@/app/components/forms/BillForm";
import { checkPermission } from '@/utils/auth';

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
            console.error('Error fetching providers:', err);
        }
    };

    const fetchBills = async () => {
        try {
            setLoading(true);
            const data = await billService.getBills(queryParams);
            setBills(data.providers);
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
        if ((type === 'create' || type === 'edit' || type === 'delete') && !checkPermission(type)) {
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
        if (!checkPermission(dialogType === 'create' ? 'create' : 'edit')) {
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
            console.error('Error saving bill:', err);
        }
    };

    const confirmDelete = async () => {
        if (!selectedBill) return;

        if (!checkPermission('delete')) {
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
            console.error('Error deleting bill:', err);
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

    return (
        <Box sx={{ p: 3 }}>
            {/* breadcrumbs nav */}
            <Breadcrumbs sx={{ mb: 3 }}>
                <Link color="inherit" href="/dashboard">
                    首页
                </Link>
                <Typography color="text.primary">订单管理</Typography>
            </Breadcrumbs>

            {/* query form */}
            <Paper sx={{ p: 2, mb: 3 }}>
                <Box sx={{ display: 'flex', gap: 2, alignItems: 'flex-end' }}>
                    <TextField
                        label="订单编码"
                        variant="outlined"
                        size="small"
                        value={queryParams.queryCode || ''}
                        onChange={handleQueryChange('queryCode')}
                    />
                    <TextField
                        label="产品名称"
                        variant="outlined"
                        size="small"
                        value={queryParams.queryProductName || ''}
                        onChange={handleQueryChange('queryProductName')}
                    />
                    <TextField
                        label="供应商编码"
                        variant="outlined"
                        size="small"
                        value={queryParams.queryProviderCode || ''}
                        onChange={handleQueryChange('queryProviderCode')}
                    />
                    <TextField
                        label="供应商名称"
                        variant="outlined"
                        size="small"
                        value={queryParams.queryProviderName || ''}
                        onChange={handleQueryChange('queryProviderName')}
                    />
                    <FormControl size="small" sx={{ minWidth: 120 }}>
                        <InputLabel>支付状态</InputLabel>
                        <Select
                            value={queryParams.queryIsPaid || ''}
                            onChange={handleQueryChange('queryIsPaid')}
                            label="支付状态"
                        >
                            <MenuItem value="">
                                <em>全部</em>
                            </MenuItem>
                            <MenuItem value={0}>未支付</MenuItem>
                            <MenuItem value={1}>已支付</MenuItem>
                        </Select>
                    </FormControl>

                    <Button
                        variant="contained"
                        onClick={() => fetchBills()}
                        disabled={loading}
                    >
                        {loading ? <CircularProgress size={24} /> : '查询'}
                    </Button>

                    <Button
                        variant="outlined"
                        startIcon={<AddIcon />}
                        onClick={() => handleOpenDialog('create')}
                    >
                        添加订单
                    </Button>
                </Box>
            </Paper>

            {/* bill list */}
            <Paper>
                <TableContainer>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>订单编码</TableCell>
                                <TableCell>产品名称</TableCell>
                                <TableCell>数量</TableCell>
                                <TableCell>总价</TableCell>
                                <TableCell>供应商</TableCell>
                                <TableCell>支付状态</TableCell>
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
                            ) : bills.length === 0 ? (
                                <TableRow>
                                    <TableCell colSpan={7} align="center">
                                        暂无数据
                                    </TableCell>
                                </TableRow>
                            ) : (
                                bills.map((bill) => (
                                    <TableRow key={bill.id}>
                                        <TableCell>{bill.code}</TableCell>
                                        <TableCell>{bill.productName}</TableCell>
                                        <TableCell>{bill.productCount} {bill.productUnit}</TableCell>
                                        <TableCell>{bill.totalPrice}</TableCell>
                                        <TableCell>
                                            {providers.find(p => p.id === bill.providerId)?.name}
                                        </TableCell>
                                        <TableCell>
                                            {bill.isPaid === 1 ? '已支付' : '未支付'}
                                        </TableCell>
                                        <TableCell>
                                            <IconButton
                                                size="small"
                                                onClick={() => handleOpenDialog('view', bill)}
                                            >
                                                <ViewIcon />
                                            </IconButton>
                                            <IconButton
                                                size="small"
                                                onClick={() => handleOpenDialog('edit', bill)}
                                            >
                                                <EditIcon />
                                            </IconButton>
                                            <IconButton
                                                size="small"
                                                onClick={() => handleDelete(bill)}
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

            {/* bill form dialog */}
            <Dialog
                open={dialogType === 'create' || dialogType === 'edit' || dialogType === 'view'}
                onClose={handleCloseDialog}
                maxWidth="md"
                fullWidth
            >
                <DialogTitle>{getDialogTitle()}</DialogTitle>
                <DialogContent>
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
                        确定要删除订单 {selectedBill?.code} 吗？
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
