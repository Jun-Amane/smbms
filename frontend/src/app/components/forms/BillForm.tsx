import React, { useEffect, useState } from 'react';
import {
    Box,
    TextField,
    Grid,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    Autocomplete,
} from '@mui/material';
import {Bill, paymentStatus, Provider} from '@/types';
import { billService } from '@/services/billService';

interface BillFormProps {
    bill?: Partial<Bill>;
    providers: Provider[];
    readOnly?: boolean;
    onChange?: (values: Partial<Bill>, isValid: boolean) => void;
    onCodeCheck?: (exists: boolean) => void;
}

type FormErrors = {
    [K in keyof Pick<Bill, 'code' | 'productName' | 'productUnit' | 'productCount' | 'totalPrice' | 'providerId'>]?: string;
};

const BillForm = React.forwardRef<{ validateForm: () => boolean }, BillFormProps>(
    function BillForm({ bill, providers, readOnly = false, onChange, onCodeCheck }, ref) {
        const [values, setValues] = useState<Partial<Bill>>(bill || {});
        const [errors, setErrors] = useState<FormErrors>({});
        const [touched, setTouched] = useState<{ [key: string]: boolean }>({});
        const selectedProvider = providers.find(p => p.id === values.providerId);

        useEffect(() => {
            if (bill) {
                setValues({
                    ...bill,
                    isPaid: bill.isPaid ?? paymentStatus.PENDING
                });
            } else {
                setValues(prevValues => ({
                    ...prevValues,
                    isPaid: paymentStatus.PENDING,
                }))
            }
        }, [bill]);

        type FieldValidator = (value: any) => string;

        const validators: {
            [K in keyof Bill]?: FieldValidator
        } = {
            code: (value: string) => {
                if (!value) return '此字段为必填项';
                return /^[A-Z]{4}\d{4}_\d{3}$/.test(value)? '' : '订单编码格式必须为BILL2016_001';
            },
            productName: (value: string) => {
                return !value ? '此字段为必填项' : '';
            },
            productUnit: (value: string) => {
                return !value ? '此字段为必填项' : '';
            },
            productCount: (value: number) => {
                if (!value) return '此字段为必填项';
                return value <= 0 ? '数量必须大于0' : '';
            },
            totalPrice: (value: number) => {
                if (!value) return '此字段为必填项';
                return value <= 0 ? '总价必须大于0' : '';
            },
            providerId: (value: number) => {
                return !value ? '此字段为必填项' : '';
            }
        };

        const validateField = (field: keyof Bill, value: any): string => {
            const validator = validators[field];
            if (validator) {
                return validator(value || '');
            }
            return '';
        };

        const validateForm = (): boolean => {
            const requiredFields = ['code', 'productName', 'productUnit', 'productCount', 'totalPrice', 'providerId'] as const;
            const newErrors: FormErrors = {};
            let isValid = true;

            requiredFields.forEach((field) => {
                const error = validateField(field, values[field]);
                if (error) {
                    newErrors[field] = error;
                    isValid = false;
                }
            });

            setErrors(newErrors);
            setTouched(requiredFields.reduce((acc, field) => ({ ...acc, [field]: true }), {}));
            return isValid;
        };

        const handleChange = (field: keyof Bill) => async (
            event: React.ChangeEvent<HTMLInputElement> | { target: { value: any } }
        ) => {
            const value = event.target.value;
            const newValues = {
                ...values,
                [field]: value,
            };

            setValues(newValues);
            setTouched(prev => ({ ...prev, [field]: true }));

            const error = validateField(field, value);
            setErrors(prev => ({ ...prev, [field]: error }));

            if (field === 'code' && !readOnly) {
                try {
                    const response = await billService.getBills({ queryCode: value });
                    const exists = response.bills?.length > 0;
                    const codeError = exists ? '订单编码已存在' : error;
                    setErrors(prev => ({ ...prev, code: codeError }));
                    onCodeCheck?.(exists);
                } catch (err) {
                    console.error('Error checking code:', err);
                }
            }

            onChange?.(newValues, !error);
        };

        // expose form validation to parent page
        React.useImperativeHandle(ref, () => ({
            validateForm
        }));

        return (
            <Box component="form" noValidate sx={{ mt: 1 }}>
                <Grid container spacing={2}>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="订单编码"
                            value={values.code || ''}
                            onChange={handleChange('code')}
                            disabled={readOnly || !!bill?.id}
                            error={touched.code && !!errors.code}
                            helperText={touched.code && errors.code}
                            required
                        />
                    </Grid>
                   <Grid item xs={6}>
                        <FormControl fullWidth required error={touched.providerId && !!errors.providerId}>
                            {readOnly ? (
                                <TextField
                                    label="供应商"
                                    value={selectedProvider ? `${selectedProvider.code} - ${selectedProvider.name}` : ''}
                                    disabled
                                    fullWidth
                                />
                            ) : (
                                <Autocomplete
                                    value={selectedProvider || null}
                                    onChange={(event, newValue) => {
                                        handleChange('providerId')({ target: { value: newValue?.id } });
                                    }}
                                    options={providers}
                                    getOptionLabel={(option) => `${option.code} - ${option.name}`}
                                    renderInput={(params) => (
                                        <TextField
                                            {...params}
                                            label="供应商"
                                            error={touched.providerId && !!errors.providerId}
                                            helperText={touched.providerId && errors.providerId}
                                            required
                                        />
                                    )}
                                    disabled={readOnly}
                                />
                            )}
                        </FormControl>
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="产品名称"
                            value={values.productName || ''}
                            onChange={handleChange('productName')}
                            disabled={readOnly}
                            error={touched.productName && !!errors.productName}
                            helperText={touched.productName && errors.productName}
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="产品单位"
                            value={values.productUnit || ''}
                            onChange={handleChange('productUnit')}
                            disabled={readOnly}
                            error={touched.productUnit && !!errors.productUnit}
                            helperText={touched.productUnit && errors.productUnit}
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="数量"
                            type="number"
                            value={values.productCount || ''}
                            onChange={handleChange('productCount')}
                            disabled={readOnly}
                            error={touched.productCount && !!errors.productCount}
                            helperText={touched.productCount && errors.productCount}
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="总价"
                            type="number"
                            value={values.totalPrice || ''}
                            onChange={handleChange('totalPrice')}
                            disabled={readOnly}
                            error={touched.totalPrice && !!errors.totalPrice}
                            helperText={touched.totalPrice && errors.totalPrice}
                            required
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="产品描述"
                            value={values.productDescription || ''}
                            onChange={handleChange('productDescription')}
                            disabled={readOnly}
                            multiline
                            rows={2}
                        />
                    </Grid>
                    {!readOnly && (
                        <Grid item xs={6}>
                            <FormControl fullWidth>
                                <InputLabel>支付状态</InputLabel>
                                <Select
                                    value={values.isPaid ?? 0}
                                    onChange={handleChange('isPaid')}
                                    label="支付状态"
                                >
                                    <MenuItem value={paymentStatus.PENDING}>未支付</MenuItem>
                                    <MenuItem value={paymentStatus.PAID}>已支付</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                    )}
                </Grid>
            </Box>
        );
    }
);

export default BillForm;
