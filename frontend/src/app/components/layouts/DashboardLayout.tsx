import React, {ReactNode, useState, useEffect} from 'react';
import {styled, useTheme} from '@mui/material/styles';
import {usePathname, useRouter} from 'next/navigation';
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
    Tooltip,
    Divider,
    useMediaQuery,
} from '@mui/material';
import {
    Menu as MenuIcon,
    Dashboard as DashboardIcon,
    ShoppingCart as OrderIcon,
    People as PeopleIcon,
    Settings as SettingsIcon,
    Logout as LogoutIcon,
    MonetizationOn as MonetizationIcon,
    ChevronLeft as ChevronLeftIcon,
} from '@mui/icons-material';

const drawerWidth = 256;

const Main = styled('main', {shouldForwardProp: (prop) => prop !== 'open'})<{
    open?: boolean;
}>(({theme, open}) => ({
    flexGrow: 1,
    transition: theme.transitions.create(['margin', 'width'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: `-${drawerWidth}px`,
    width: `calc(100% + ${drawerWidth}px)`,
    ...(open && {
        transition: theme.transitions.create(['margin', 'width'], {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
        marginLeft: 0,
        width: '100%',
    }),
}));

const DrawerHeader = styled('div')(({theme}) => ({
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
}));

interface DashboardLayoutProps {
    children: ReactNode;
}

export default function DashboardLayout({children}: DashboardLayoutProps) {
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
    const [open, setOpen] = useState(!isMobile);
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const [userName, setUserName] = useState<string>('');
    const router = useRouter();
    const pathname = usePathname();

    useEffect(() => {
        const token = localStorage.getItem('token');
        const storedUserName = localStorage.getItem('userName');

        if (!token) {
            router.push('/auth/login');
            return;
        }

        if (storedUserName) {
            setUserName(storedUserName);
        }
    }, [router]);

    useEffect(() => {
        setOpen(!isMobile);
    }, [isMobile]);

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
        {text: '控制台主页', icon: <DashboardIcon/>, path: '/dashboard'},
        {text: '订单管理', icon: <MonetizationIcon/>, path: '/bills'},
        {text: '供应商管理', icon: <OrderIcon/>, path: '/providers'},
        {text: '用户管理', icon: <PeopleIcon/>, path: '/users'},
    ];

    const drawer = (
        <>
            <DrawerHeader>
                <Typography variant="h6" sx={{flexGrow: 1, ml: 2}}>
                    SMBMS
                </Typography>
                {open && (
                    <IconButton onClick={handleDrawerToggle}>
                        <ChevronLeftIcon/>
                    </IconButton>
                )}
            </DrawerHeader>
            <Divider/>
            <List>
                {menuItems.map((item) => (
                    <ListItem key={item.text} disablePadding>
                        <ListItemButton
                            onClick={() => router.push(item.path)}
                            selected={pathname === item.path}
                            sx={{
                                minHeight: 48,
                                px: 2.5,
                                '&.Mui-selected': {
                                    backgroundColor: 'primary.light',
                                    color: 'white',
                                    '& .MuiListItemIcon-root': {
                                        color: 'white',
                                    },
                                    '&:hover': {
                                        backgroundColor: 'primary.main',
                                    },
                                },
                            }}
                        >
                            <ListItemIcon
                                sx={{
                                    minWidth: 0,
                                    mr: open ? 2 : 'auto',
                                    justifyContent: 'center',
                                    color: pathname === item.path ? 'white' : 'inherit',
                                }}
                            >
                                {item.icon}
                            </ListItemIcon>
                            {open && <ListItemText primary={item.text}/>}
                        </ListItemButton>
                    </ListItem>
                ))}
            </List>
        </>
    );

    return (
        <Box sx={{display: 'flex', minHeight: '100vh'}}>
            <AppBar
                position="fixed"
                sx={{
                    zIndex: theme.zIndex.drawer + 1,
                    bgcolor: 'primary.main',
                }}
            >
                <Toolbar>
                    <IconButton
                        color="inherit"
                        edge="start"
                        onClick={handleDrawerToggle}
                        sx={{mr: 2}}
                    >
                        <MenuIcon/>
                    </IconButton>
                    <Typography
                        variant="h6"
                        noWrap
                        component="div"
                        sx={{
                            flexGrow: 1,
                            display: 'flex',
                            alignItems: 'center',
                            gap: 1
                        }}
                    >
                        超市订单管理系统
                    </Typography>
                    <Box sx={{display: 'flex', alignItems: 'center', gap: 2}}>
                        <Typography variant="body2">
                            {userName}
                        </Typography>
                        <Tooltip title="账户设置">
                            <IconButton
                                onClick={handleMenu}
                                sx={{
                                    p: 0.5,
                                    border: '2px solid rgba(255, 255, 255, 0.2)',
                                    '&:hover': {
                                        border: '2px solid rgba(255, 255, 255, 0.3)',
                                    }
                                }}
                            >
                                <Avatar
                                    sx={{
                                        width: 32,
                                        height: 32,
                                        bgcolor: 'primary.dark'
                                    }}
                                >
                                    {userName?.[0]?.toUpperCase()}
                                </Avatar>
                            </IconButton>
                        </Tooltip>
                    </Box>
                    <Menu
                        anchorEl={anchorEl}
                        open={Boolean(anchorEl)}
                        onClose={handleClose}
                        transformOrigin={{horizontal: 'right', vertical: 'top'}}
                        anchorOrigin={{horizontal: 'right', vertical: 'bottom'}}
                        PaperProps={{
                            elevation: 0,
                            sx: {
                                mt: 1.5,
                                overflow: 'visible',
                                filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.1))',
                                '&:before': {
                                    content: '""',
                                    display: 'block',
                                    position: 'absolute',
                                    top: 0,
                                    right: 14,
                                    width: 10,
                                    height: 10,
                                    bgcolor: 'background.paper',
                                    transform: 'translateY(-50%) rotate(45deg)',
                                    zIndex: 0,
                                },
                            },
                        }}
                    >
                        <MenuItem onClick={handlePasswordChange}>
                            <ListItemIcon>
                                <SettingsIcon fontSize="small"/>
                            </ListItemIcon>
                            修改密码
                        </MenuItem>
                        <MenuItem onClick={handleLogout}>
                            <ListItemIcon>
                                <LogoutIcon fontSize="small"/>
                            </ListItemIcon>
                            退出登录
                        </MenuItem>
                    </Menu>
                </Toolbar>
            </AppBar>
            <Drawer
                variant={isMobile ? 'temporary' : 'persistent'}
                anchor="left"
                open={open}
                onClose={isMobile ? handleDrawerToggle : undefined}
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    '& .MuiDrawer-paper': {
                        width: drawerWidth,
                        boxSizing: 'border-box',
                        bgcolor: 'background.paper',
                    },
                }}
            >
                {drawer}
            </Drawer>
            <Main open={!isMobile && open}>
                <Toolbar/>
                <Box
                    sx={{
                        p: 3,
                        bgcolor: 'background.default',
                        minHeight: 'calc(100vh - 64px)',
                    }}
                >
                    {children}
                </Box>
            </Main>
        </Box>
    );
}

