'use client'

import DashboardLayout from '@/app/components/layouts/DashboardLayout';
import React from "react";

export default function Layout({ children }: { children: React.ReactNode }) {
  return <DashboardLayout>{children}</DashboardLayout>;
}

