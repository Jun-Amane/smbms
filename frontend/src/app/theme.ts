import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
    background: {
      default: '#ffffff',
      paper: '#ffffff',
    },
  },
  typography: {
    fontFamily: 'var(--font-geist-sans), Arial, sans-serif',
    h1: {
      fontFamily: 'var(--font-geist-sans), Arial, sans-serif',
    },
    h2: {
      fontFamily: 'var(--font-geist-sans), Arial, sans-serif',
    },
    h3: {
      fontFamily: 'var(--font-geist-sans), Arial, sans-serif',
    },
    h4: {
      fontFamily: 'var(--font-geist-sans), Arial, sans-serif',
    },
    h5: {
      fontFamily: 'var(--font-geist-sans), Arial, sans-serif',
    },
    h6: {
      fontFamily: 'var(--font-geist-sans), Arial, sans-serif',
    },
  },
  components: {
    MuiAppBar: {
      styleOverrides: {
        root: {
          backgroundColor: '#1976d2',
        },
      },
    },
    MuiDrawer: {
      styleOverrides: {
        paper: {
          backgroundColor: '#ffffff',
          borderRight: '1px solid rgba(0, 0, 0, 0.12)',
        },
      },
    },
  },
});

export default theme;