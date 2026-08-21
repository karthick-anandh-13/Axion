export interface BorrowerOpportunity {
  id: string;

  title: string;

  amount: number;

  tenureMonths: number;

  interestRate: number;

  aiRiskScore: number;

  purpose: string;
}