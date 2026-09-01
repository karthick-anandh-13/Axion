import { useEffect, useState } from "react";

export interface EmiSchedule {
  id: string;
  dueDate: string;
  amount: number;
  status: "PAID" | "UPCOMING" | "OVERDUE";
}

export interface RepaymentSummary {
  progress: number;
  remainingAmount: number;
  nextEmi: number;
  schedule: EmiSchedule[];
}

export function useRepayment() {
  const [data, setData] = useState<RepaymentSummary | null>(null);

  useEffect(() => {
    // Development mock data
    setTimeout(() => {
      setData({
        progress: 62,
        remainingAmount: 480000,
        nextEmi: 18420,
        schedule: [
          {
            id: "EMI-001",
            dueDate: "10 Sep 2026",
            amount: 18420,
            status: "UPCOMING",
          },
          {
            id: "EMI-002",
            dueDate: "10 Aug 2026",
            amount: 18420,
            status: "PAID",
          },
          {
            id: "EMI-003",
            dueDate: "10 Jul 2026",
            amount: 18420,
            status: "PAID",
          },
          {
            id: "EMI-004",
            dueDate: "10 Jun 2026",
            amount: 18420,
            status: "PAID",
          },
          {
            id: "EMI-005",
            dueDate: "10 May 2026",
            amount: 18420,
            status: "PAID",
          },
          {
            id: "EMI-006",
            dueDate: "10 Apr 2026",
            amount: 18420,
            status: "OVERDUE",
          },
        ],
      });
    }, 600); // simulate API latency
  }, []);

  return {
    data,
    isLoading: data === null,
  };
}