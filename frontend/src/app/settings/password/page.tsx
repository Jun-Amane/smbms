'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import {
  Box,
  Card,
  CardContent,
  Typography,
  TextField,
  Button,
  Alert,
  CircularProgress,
} from '@mui/material';
import { Lock as LockIcon } from '@mui/icons-material';
import type { PasswordUpdateRequest } from '@/types/auth';
import { userService } from '@/services/userService';

export default function PasswordPage() {
  const router = useRouter();
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);
  const [loading, setLoading] = useState(false);

  const validatePassword = () => {
    if (!oldPassword) {
      setError('请输入原密码');
      return false;
    }
    if (newPassword.length < 6) {
      setError('新密码长度至少为6位');
      return false;
    }
    if (newPassword === oldPassword) {
      setError('新密码不能与原密码相同');
      return false;
    }
    if (newPassword !== confirmPassword) {
      setError('两次输入的密码不一致');
      return false;
    }
    return true;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    if (!validatePassword()) {
      return;
    }

    const userId = localStorage.getItem('userId');
    if (!userId) {
      router.push('/auth/login');
      return;
    }

    setLoading(true);
    try {
      const passwordReq: PasswordUpdateRequest = {
        oldPassword,
        newPassword,
      };

      await userService.updatePassword(parseInt(userId), passwordReq);
      setSuccess(true);

      // 清空表单
      setOldPassword('');
      setNewPassword('');
      setConfirmPassword('');

      // 3秒后返回仪表板
      setTimeout(() => {
        router.push('/dashboard');
      }, 3000);
    } catch (err: any) {
      setError(err.response?.data?.message || '修改密码失败，请重试');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box
      sx={{
        maxWidth: 'sm',
        mx: 'auto',
        mt: 4
      }}
    >
      <Card>
        <CardContent sx={{ p: 4 }}>
          <Box sx={{
            display: 'flex',
            alignItems: 'center',
            gap: 1,
            mb: 3
          }}>
            <LockIcon color="primary" />
            <Typography variant="h5" component="h1">
              修改密码
            </Typography>
          </Box>

          {error && (
            <Alert severity="error" sx={{ mb: 3 }}>
              {error}
            </Alert>
          )}

          {success && (
            <Alert severity="success" sx={{ mb: 3 }}>
              密码修改成功！3秒后返回仪表板...
            </Alert>
          )}

          <form onSubmit={handleSubmit}>
            <TextField
              fullWidth
              type="password"
              label="原密码"
              value={oldPassword}
              onChange={(e) => setOldPassword(e.target.value)}
              disabled={loading}
              sx={{ mb: 3 }}
            />

            <TextField
              fullWidth
              type="password"
              label="新密码"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              disabled={loading}
              sx={{ mb: 3 }}
            />

            <TextField
              fullWidth
              type="password"
              label="确认新密码"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              disabled={loading}
              sx={{ mb: 3 }}
            />

            <Button
              type="submit"
              variant="contained"
              fullWidth
              disabled={loading}
              sx={{
                py: 1.5,
                position: 'relative'
              }}
            >
              {loading && (
                <CircularProgress
                  size={24}
                  sx={{
                    position: 'absolute',
                    left: '50%',
                    marginLeft: '-12px',
                  }}
                />
              )}
              {!loading && '确认修改'}
            </Button>
          </form>
        </CardContent>
      </Card>
    </Box>
  );
}
