export interface CreateLoanRequest {
  requestedAmount: number;
  requestedTenureMonths: number;
  maximumAcceptableApr: number;
  purpose:
    | "PERSONAL"
    | "BUSINESS"
    | "EDUCATION"
    | "MEDICAL"
    | "EMERGENCY"
    | "HOME_REPAIR"
    | "VEHICLE"
    | "DEBT_CONSOLIDATION"
    | "AGRICULTURE"
    | "OTHER";
  purposeDescription: string;
}

export interface LoanResponse {
  id: string;
  status: string;
  requestedAmount: number;
  requestedTenureMonths: number;
  maximumAcceptableApr: number;
  purpose: string;
  createdAt: string;
}