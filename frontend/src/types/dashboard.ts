export interface DashboardMetric {
  label: string;
  value: number;
  change?: number;
  currency?: boolean;
}

export interface DashboardSummary {
  availableCredit: number;
  outstandingBalance: number;
  nextPaymentAmount: number;
  nextPaymentDate?: string;
  metrics: DashboardMetric[];
}
