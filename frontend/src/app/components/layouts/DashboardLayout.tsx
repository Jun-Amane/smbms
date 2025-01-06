'use client';

import React, { ReactNode, useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { usePathname, useRouter } from 'next/navigation';
import {
    Box,
    Drawer,
    AppBar,
    Toolbar,
    List,
    Typography,
    IconButton,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    Menu,
    MenuItem,
    Avatar,
} from '@mui/material';
import {
    Menu as MenuIcon,
    Dashboard as DashboardIcon,
    ShoppingCart as OrderIcon,
    People as SupplierIcon,
    Group as UserIcon,
    Settings as SettingsIcon,
    Logout as LogoutIcon,
    MonetizationOn as MonetizationIcon,
} from '@mui/icons-material';

const drawerWidth = 240;

const Main = styled('main', { shouldForwardProp: (prop) => prop !== 'open' })<{
    open?: boolean;
}>(({ theme, open }) => ({
    flexGrow: 1,
    padding: theme.spacing(3),
    transition: theme.transitions.create('margin', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: `-${drawerWidth}px`,
    ...(open && {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
        marginLeft: 0,
    }),
}));

interface DashboardLayoutProps {
    children: ReactNode;
}

export default function DashboardLayout({ children }: DashboardLayoutProps) {
    const [open, setOpen] = useState(true);
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const [userName, setUserName] = useState<string>('');
    const router = useRouter();
    const pathname = usePathname();

    useEffect(() => {
        // check login status
        const token = localStorage.getItem('token');
        const storedUserName = localStorage.getItem('userName');

        if (!token) {
            router.push('/auth/login');
            return;
        }

        if (storedUserName) {
            setUserName(storedUserName);
        }
    }, []);

    const handleDrawerToggle = () => {
        setOpen(!open);
    };

    const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleLogout = () => {
        // remove all stored info
        localStorage.removeItem('token');
        localStorage.removeItem('userCode');
        localStorage.removeItem('userId');
        localStorage.removeItem('userName');

        handleClose();
        router.push('/auth/login');
    };

    const handlePasswordChange = () => {
        handleClose();
        router.push('/settings/password');
    };

    const menuItems = [
        { text: '控制台主页', icon: <DashboardIcon />, path: '/dashboard' },
        { text: '订单管理', icon: < MonetizationIcon/>, path: '/bills' },
        { text: '供应商管理', icon: <OrderIcon />, path: '/providers' },
        { text: '用户管理', icon: <UserIcon />, path: '/users' },
    ];

    return (
        <Box sx={{ display: 'flex' }}>
            <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
                <Toolbar>
                    <IconButton
                        color="inherit"
                        edge="start"
                        onClick={handleDrawerToggle}
                        sx={{ mr: 2 }}
                    >
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1 }}>
                        超市订单管理系统 (SMBMS)
                    </Typography>
                    <Typography variant="body1" sx={{ mr: 2 }}>
                        {userName}
                    </Typography>
                    <IconButton
                        size="large"
                        onClick={handleMenu}
                        color="inherit"
                    >
                        <Avatar />
                    </IconButton>
                    <Menu
                        anchorEl={anchorEl}
                        open={Boolean(anchorEl)}
                        onClose={handleClose}
                    >
                        <MenuItem onClick={handlePasswordChange}>
                            <ListItemIcon>
                                <SettingsIcon fontSize="small" />
                            </ListItemIcon>
                            修改密码
                        </MenuItem>
                        <MenuItem onClick={handleLogout}>
                            <ListItemIcon>
                                <LogoutIcon fontSize="small" />
                            </ListItemIcon>
                            退出登录
                        </MenuItem>
                    </Menu>
                </Toolbar>
            </AppBar>
            <Drawer
                variant="persistent"
                anchor="left"
                open={open}
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    '& .MuiDrawer-paper': {
                        width: drawerWidth,
                        boxSizing: 'border-box',
                    },
                }}
            >
                <Toolbar />
                <Box sx={{ overflow: 'auto' }}>
                    <List>
                        {menuItems.map((item) => (
                            <ListItem key={item.text} disablePadding>
                                <ListItemButton
                                    onClick={() => router.push(item.path)}
                                    selected={pathname === item.path}
                                >
                                    <ListItemIcon>{item.icon}</ListItemIcon>
                                    <ListItemText primary={item.text} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                </Box>
            </Drawer>
            <Main open={open}>
                <Toolbar />
                {children}
            </Main>
        </Box>
    );
}

