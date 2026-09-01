export type UserType = "mock" | "real";

export interface Investment {
  id: string;
  loanId: string;
  borrowerName: string;
  loanPurpose: string;
  amount: number;
  apr: number;
  tenure: number;
  platformFee: number;
  gst: number;
  totalAmount: number;
  aiRiskScore: number;
}

export interface PaymentState {
  userType: UserType;
  investment: Investment | null;
  status: "pending" | "processing" | "success" | "failed";
  investmentId?: string;
  timestamp?: string;
}

export interface TransferStep {
  id: string;
  label: string;
  completed: boolean;
  current: boolean;
}
