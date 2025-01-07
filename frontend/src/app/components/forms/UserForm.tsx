import React, {useEffect, useState} from 'react';

import {
    Box,
    TextField,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    Grid,
    RadioGroup,
    FormControlLabel,
    Radio,
    FormHelperText,
} from '@mui/material';
import {User, Role, Gender} from '@/types/user';
import {userService} from '@/services/userService';

interface UserFormProps {
    user?: Partial<User>;
    roles: Role[];
    readOnly?: boolean;
    onChange?: (values: Partial<User>, isValid: boolean) => void;
    onCodeCheck?: (exists: boolean) => void;
}

type FormErrors = {
    [K in keyof Pick<User, 'code' | 'name' | 'gender' | 'birthday' | 'phone' | 'address' | 'roleId' | 'password'>]?: string;
};

const UserForm = React.forwardRef<{ validateForm: () => boolean }, UserFormProps>(
    function UserForm({user, roles, readOnly = false, onChange, onCodeCheck}, ref) {
        const [values, setValues] = useState<Partial<User>>(user || {});
        const [errors, setErrors] = useState<FormErrors>({});
        const [touched, setTouched] = useState<{ [key: string]: boolean }>({});

        useEffect(() => {
            if (user) {
                setValues({
                    ...user,
                    gender: user.gender ?? Gender.Other
                });
            } else {
                setValues(prevValues => ({
                    ...prevValues,
                    gender: Gender.Other
                }));
            }
        }, [user]);

        type FieldValidator<T> = (value: T) => string;

        const validators: {
            [K in keyof User]?: FieldValidator<User[K]>
        } = {
            phone: (value: string) => {
                if (!value) return '此字段为必填项';
                return /^1[3-9]\d{9}$/.test(value) ? '' : '请输入有效的手机号码';
            },
            code: (value: string) => {
                if (!value) return '此字段为必填项';
                return /^[A-Za-z0-9]{4,20}$/.test(value) ? '' : '用户编码必须是4-20位字母或数字';
            },
            password: (value: string | undefined) => {
                if (!user?.id && !value) return '此字段为必填项';
                if (!user?.id && value && value.length < 6) return '密码长度至少为6位';
                return '';
            },
            roleId: (value: number | undefined) => {
                return typeof value === 'undefined' ? '此字段为必填项' : '';
            },
            name: (value: string) => {
                return !value ? '此字段为必填项' : '';
            },
            gender: (value: Gender | undefined) => {
                return typeof value === 'undefined' ? '此字段为必填项' : '';
            },
            birthday: (value: string) => {
                return !value ? '此字段为必填项' : '';
            },
            address: (value: string) => {
                return !value ? '此字段为必填项' : '';
            }
        };

        const validateField = (field: keyof User, value: User[keyof User]): string => {
            const validator = validators[field];
            if (validator) {
                return validator(value as never);
            }
            return !value ? '此字段为必填项' : '';
        };

        const validateForm = (): boolean => {
            const requiredFields = ['code', 'name', 'gender', 'birthday', 'phone', 'roleId', 'address'] as const;
            const fieldsToValidate = user?.id
                ? requiredFields
                : [...requiredFields, 'password'] as const;

            const newErrors: FormErrors = {};
            let isValid = true;

            fieldsToValidate.forEach((field) => {
                const error = validateField(field, values[field]);
                if (error) {
                    newErrors[field] = error;
                    isValid = false;
                }
            });

            setErrors(newErrors);
            setTouched(fieldsToValidate.reduce((acc, field) => ({...acc, [field]: true}), {}));
            return isValid;
        };

        const handleChange = (field: keyof User) => async (
            event: React.ChangeEvent<HTMLInputElement> | { target: { value: User[keyof User] } }
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

            // Check code uniqueness
            if (field === 'code' && !readOnly) {
                try {
                    const response = await userService.checkCodeExists(value as string);
                    const exists = response.result === 'exists';
                    const codeError = exists ? '用户编码已存在' : error;
                    setErrors(prev => ({...prev, code: codeError}));
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
            <Box component="form" noValidate sx={{mt: 1}}>
                <Grid container spacing={2}>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="用户编码"
                            value={values.code || ''}
                            onChange={handleChange('code')}
                            disabled={readOnly || !!user?.id}
                            error={touched.code && !!errors.code}
                            helperText={touched.code && errors.code}
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="用户名称"
                            value={values.name || ''}
                            onChange={handleChange('name')}
                            disabled={readOnly}
                            error={touched.name && !!errors.name}
                            helperText={touched.name && errors.name}
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <FormControl error={touched.gender && !!errors.gender} required>
                            <RadioGroup
                                row
                                value={values.gender || Gender.Other}
                                onChange={handleChange('gender')}
                            >
                                <FormControlLabel
                                    value={Gender.Male}
                                    control={<Radio/>}
                                    label="男"
                                    disabled={readOnly}
                                />
                                <FormControlLabel
                                    value={Gender.Female}
                                    control={<Radio/>}
                                    label="女"
                                    disabled={readOnly}
                                />
                                <FormControlLabel
                                    value={Gender.Other}
                                    control={<Radio/>}
                                    label="其他"
                                    disabled={readOnly}
                                />
                            </RadioGroup>
                            {touched.gender && errors.gender && (
                                <FormHelperText>{errors.gender}</FormHelperText>
                            )}
                        </FormControl>
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            fullWidth
                            label="出生日期"
                            type="date"
                            value={values.birthday || ''}
                            onChange={handleChange('birthday')}
                            disabled={readOnly}
                            error={touched.birthday && !!errors.birthday}
                            helperText={touched.birthday && errors.birthday}
                            InputLabelProps={{
                                shrink: true,
                            }}
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
                        <FormControl
                            fullWidth
                            error={touched.roleId && !!errors.roleId}
                            required
                        >
                            <InputLabel>用户角色</InputLabel>
                            <Select
                                value={values.roleId || ''}
                                onChange={handleChange('roleId')}
                                label="用户角色"
                                disabled={readOnly}
                            >
                                {roles.map((role) => (
                                    <MenuItem key={role.id} value={role.id}>
                                        {role.name}
                                    </MenuItem>
                                ))}
                            </Select>
                            {touched.roleId && errors.roleId && (
                                <FormHelperText>{errors.roleId}</FormHelperText>
                            )}
                        </FormControl>
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
                    {!readOnly && !user?.id && (
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="密码"
                                type="password"
                                value={values.password || ''}
                                onChange={handleChange('password')}
                                error={touched.password && !!errors.password}
                                helperText={touched.password && errors.password}
                                required
                            />
                        </Grid>
                    )}
                </Grid>
            </Box>
        );
    }
);

export default UserForm;
