import React, {useEffect, useState} from 'react';
import {
    Box,
    TextField,
    Grid,
} from '@mui/material';
import {Provider} from '@/types';
import {providerService} from '@/services/providerService';

interface ProviderFormProps {
    provider?: Partial<Provider>;
    readOnly?: boolean;
    onChange?: (values: Partial<Provider>, isValid: boolean) => void;
    onCodeCheck?: (exists: boolean) => void;
}

type FormErrors = {
    [K in keyof Pick<Provider, 'code' | 'name' | 'description' | 'address' | 'phone' | 'fax' | 'contact'>]?: string;
};

const ProviderForm = React.forwardRef<{ validateForm: () => boolean }, ProviderFormProps>(
    function ProviderForm({provider, readOnly = false, onChange, onCodeCheck}, ref) {
        const [values, setValues] = useState<Partial<Provider>>(provider || {});
        const [errors, setErrors] = useState<FormErrors>({});
        const [touched, setTouched] = useState<{ [key: string]: boolean }>({});

        useEffect(() => {
            if (provider) {
                setValues(provider);
            }
        }, [provider]);

        type FieldValidator<T> = (value: T) => string;

        const validators: {
            [K in keyof Provider]?: FieldValidator<Provider[K]>
        } = {
            phone: (value: string) => {
                if (!value) return '此字段为必填项';
                return /^\d{7,11}$/.test(value) ? '' : '请输入有效的电话号码';
            },
            code: (value: string) => {
                if (!value) return '此字段为必填项';
                return /^[A-Z]{2}_[A-Z]{2}\d{3}$/.test(value) ? '' : '供应商编码格式必须为AB_CD001';
            },
            name: (value: string) => {
                return !value ? '此字段为必填项' : '';
            },
            address: (value: string) => {
                return !value ? '此字段为必填项' : '';
            },
            contact: (value: string) => {
                return !value ? '此字段为必填项' : '';
            }
        };

        const validateField = (field: keyof Provider, value: Provider[keyof Provider] | unknown): string => {
            const validator = validators[field];
            if (validator) {
                return validator(value as never);
            }
            return '';
        };

        const validateForm = (): boolean => {
            const requiredFields = ['code', 'name', 'address', 'phone', 'contact'] as const;
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
            setTouched(requiredFields.reduce((acc, field) => ({...acc, [field]: true}), {}));
            return isValid;
        };

        const handleChange = (field: keyof Provider) => async (
            event: React.ChangeEvent<HTMLInputElement>
        ) => {
            const value = event.target.value;
            const newValues = {
                ...values,
                [field]: value,
            };

            setValues(newValues);
            setTouched(prev => ({...prev, [field]: true}));

            const error = validateField(field, value);
            setErrors(prev => ({...prev, [field]: error}));

            onChange?.(newValues, !error);

            // Check code uniqueness when code field changes
            if (field === 'code' && !readOnly && value) {
                try {
                    const response = await providerService.getProviders({queryCode: value});
                    const exists = response.providers.length > 0;
                    const codeError = exists ? '供应商编码已存在' : error;
                    setErrors(prev => ({...prev, code: codeError}));
                    onCodeCheck?.(exists);
                } catch (err) {
                    console.error('Error checking code:', err);
                }
            }
        };

        // Expose form validation to parent component
        React.useImperativeHandle(ref, () => ({
            validateForm
        }));

        return (
            <Box component="form" noValidate sx={{mt: 1}}>
                <Grid container spacing={2}>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="供应商编码"
                            value={values.code || ''}
                            onChange={handleChange('code')}
                            disabled={readOnly || !!provider?.id}
                            error={touched.code && !!errors.code}
                            helperText={touched.code && errors.code}
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="供应商名称"
                            value={values.name || ''}
                            onChange={handleChange('name')}
                            disabled={readOnly}
                            error={touched.name && !!errors.name}
                            helperText={touched.name && errors.name}
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="联系人"
                            value={values.contact || ''}
                            onChange={handleChange('contact')}
                            disabled={readOnly}
                            error={touched.contact && !!errors.contact}
                            helperText={touched.contact && errors.contact}
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="电话"
                            value={values.phone || ''}
                            onChange={handleChange('phone')}
                            disabled={readOnly}
                            error={touched.phone && !!errors.phone}
                            helperText={touched.phone && errors.phone}
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="传真"
                            value={values.fax || ''}
                            onChange={handleChange('fax')}
                            disabled={readOnly}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="描述"
                            value={values.description || ''}
                            onChange={handleChange('description')}
                            disabled={readOnly}
                            multiline
                            rows={2}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="地址"
                            value={values.address || ''}
                            onChange={handleChange('address')}
                            disabled={readOnly}
                            error={touched.address && !!errors.address}
                            helperText={touched.address && errors.address}
                            multiline
                            rows={2}
                            required
                        />
                    </Grid>
                </Grid>
            </Box>
        );
    }
);

export default ProviderForm;

