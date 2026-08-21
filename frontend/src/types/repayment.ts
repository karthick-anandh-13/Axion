export interface EmiSchedule {
  id: string;
  dueDate: string;
  amount: number;
  status: "PAID" | "DUE" | "UPCOMING";
}

export interface RepaymentSummary {
  remainingAmount: number;
  paidAmount: number;
  progress: number;
  nextEmi: number;
  schedule: EmiSchedule[];
}