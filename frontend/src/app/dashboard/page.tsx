'use client'

import React, {useEffect, useState} from 'react';
import {
    Container,
    Typography,
    Grid,
    Card,
    CardContent,
    CircularProgress, Paper, Box
} from '@mui/material';
import ReactEcharts from 'echarts-for-react';
import {billService} from '@/services/billService';
import {dashboardService} from '@/services/dashboardService';
import {BillStatsDto, DashboardStatsDto, ProviderStatsDto} from '@/types';
import {providerService} from "@/services/providerService";
import theme from '@/app/theme'

export default function DashboardPage() {
    const [stats, setStats] = useState<DashboardStatsDto | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [billStats, setBillStats] = useState<BillStatsDto | null>(null);
    const [providerStats, setProviderStats] = useState<ProviderStatsDto | null>(null);

    // Fetch bill statistics
    const fetchBillStats = async () => {
        try {
            const data = await billService.getBillStats();
            setBillStats(data);
        } catch (err) {
            console.error('Error fetching bill stats:', err);
        } finally {
            setLoading(false);
        }
    };

    const fetchProviderStats = async () => {
        try {
            const data = await providerService.getProviderStats()
            setProviderStats(data);
        } catch (err) {
            console.error('Error fetching provider stats:', err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await dashboardService.getDashboardStats();
                setStats(data);
            } catch (error) {
                console.error("Error fetching dashboard stats:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
        fetchBillStats(); // Fetch statistics at component mount
        fetchProviderStats();
    }, []);

    if (loading) {
        return <CircularProgress/>;
    }

    const getProviderOrderContributionOption = () => ({
        title: {
            text: '供应商订单贡献比例',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        // legend: {
        //     orient: 'vertical',
        //     left: 'left'
        // },
        series: [
            {
                name: '订单数量',
                type: 'pie',
                radius: '50%',
                data: providerStats?.providerOrderContributions.map(contribution => ({
                    value: contribution.orderCount,
                    name: contribution.providerName
                })),
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    });

    const getProviderSalesOption = () => ({
        title: {
            text: '供应商商品销售总额',
            left: 'center'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        xAxis: {
            type: 'category',
            data: providerStats?.providerSales.map(sale => sale.providerName),
            axisTick: {
                alignWithLabel: true
            }
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '销售额',
                type: 'bar',
                barWidth: '60%',
                data: providerStats?.providerSales.map(sale => sale.totalSalesAmount),
                itemStyle: {
                    color: theme.palette.primary.dark,
                },
            }
        ]
    });

    // Echarts options for payment status pie chart
    const getPaymentStatusOption = () => ({
        title: {
            text: '订单支付状态',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [
            {
                name: '支付状态',
                type: 'pie',
                radius: '50%',
                data: billStats?.paymentStatus.map(item => ({
                    value: item.count,
                    name: item.status === 'paid' ? '已支付' : '未支付'
                })),
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    });

    // Echarts options for product sales bar chart
    const getProductSalesOption = () => ({
        title: {
            text: '商品销售额',
            left: 'center'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        xAxis: {
            type: 'category',
            data: billStats?.productSales.map(sale => sale.productName),
            axisTick: {
                alignWithLabel: true
            }
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '销售额',
                type: 'bar',
                barWidth: '60%',
                data: billStats?.productSales.map(sale => sale.totalSalesAmount),
                itemStyle: {
                    color: theme.palette.primary.dark,
                },
            }
        ]
    });

    const getPeriodicalOptions = () => ({
        title: {
            text: '季度订单统计',
            textStyle: {
                color: theme.palette.grey[800],
                fontFamily: theme.typography.fontFamily,
            },
        },
        tooltip: {
            trigger: 'axis',
        },
        legend: {
            data: ['订单数量', '商品总额'],
            textStyle: {
                color: theme.palette.grey[600],
            },
        },
        xAxis: {
            type: 'category',
            data: stats?.quarterlyStats.map(q => q.quarter),
            axisLine: {
                lineStyle: {
                    color: theme.palette.grey[400],
                },
            },
        },
        yAxis: [
            {
                type: 'value',
                name: '订单数量',
                axisLine: {
                    lineStyle: {
                        color: theme.palette.grey[400],
                    },
                },
                splitLine: {
                    lineStyle: {
                        color: theme.palette.grey[200],
                    },
                },
            },
            {
                type: 'value',
                name: '商品总额',
                axisLine: {
                    lineStyle: {
                        color: theme.palette.grey[400],
                    },
                },
                splitLine: {
                    lineStyle: {
                        color: theme.palette.grey[200],
                    },
                },
            },
        ],
        series: [
            {
                name: '订单数量',
                type: 'bar',
                data: stats?.quarterlyStats.map(q => q.orderCount),
                itemStyle: {
                    color: theme.palette.primary.dark,
                },
            },
            {
                name: '商品总额',
                type: 'line',
                yAxisIndex: 1,
                data: stats?.quarterlyStats.map(q => q.totalAmount),
                itemStyle: {
                    color: theme.palette.secondary.light,
                },
                lineStyle: {
                    width: 2,
                },
            },
        ],
    });


    return (
        <Container>
            <Typography variant="h4" component="h1" gutterBottom>
                欢迎来到控制台主页
            </Typography>
            <Grid container spacing={2} marginBottom={2}>
                <Grid item xs={12} sm={6} md={3}>
                    <Card>
                        <CardContent>
                            <Typography variant="h5">{stats?.orderCount}</Typography>
                            <Typography color="textSecondary">订单数量</Typography>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid item xs={12} sm={6} md={3}>
                    <Card>
                        <CardContent>
                            <Typography variant="h5">{stats?.totalOrderAmount.toFixed(2)}</Typography>
                            <Typography color="textSecondary">订单总额</Typography>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid item xs={12} sm={6} md={3}>
                    <Card>
                        <CardContent>
                            <Typography variant="h5">{stats?.providerCount}</Typography>
                            <Typography color="textSecondary">供应商数量</Typography>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid item xs={12} sm={6} md={3}>
                    <Card>
                        <CardContent>
                            <Typography variant="h5">{stats?.userCount}</Typography>
                            <Typography color="textSecondary">用户数量</Typography>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>

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
                <Box sx={{mb: 3}}>
                    <Typography variant="h6" sx={{mb: 2}}>供应商统计</Typography>
                    {loading ? <CircularProgress/> : (
                            <Grid item xs={12} md={6}>
                                <ReactEcharts option={getPeriodicalOptions()} style={{height: '400px', width: '100%'}}/>
                            </Grid>
                    )}
                </Box>
            </Paper>
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
                {/* Chart Section */}
                <Box sx={{mb: 3}}>
                    <Typography variant="h6" sx={{mb: 2}}>供应商统计</Typography>
                    {loading ? <CircularProgress/> : (
                        <Grid container spacing={3}>
                            <Grid item xs={12} md={6}>
                                <ReactEcharts option={getProviderOrderContributionOption()}/>
                            </Grid>
                            <Grid item xs={12} md={6}>
                                <ReactEcharts option={getProviderSalesOption()}/>
                            </Grid>
                        </Grid>
                    )}
                </Box>
            </Paper>

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
                {/* Chart Section */}
                <Box sx={{mb: 3}}>
                    <Typography variant="h6" sx={{mb: 2}}>订单统计</Typography>
                    {loading ? <CircularProgress/> : (
                        <Grid container spacing={3}>
                            <Grid item xs={12} md={6}>
                                <ReactEcharts option={getPaymentStatusOption()}/>
                            </Grid>
                            <Grid item xs={12} md={6}>
                                <ReactEcharts option={getProductSalesOption()}/>
                            </Grid>
                        </Grid>
                    )}
                </Box>
            </Paper>
        </Container>
    );
}
