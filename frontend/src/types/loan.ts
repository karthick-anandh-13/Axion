export interface CreateLoanRequest {
  amount: number;
  tenureMonths: number;
  purpose: string;
}

export interface LoanResponse {
  id: string;
  amount: number;
  tenureMonths: number;
  interestRate: number;
  purpose: string;
  status: string;
  createdAt: string;
}