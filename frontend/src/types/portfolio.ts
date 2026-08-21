export interface PortfolioSummary {
  totalPortfolio: number;
  activeLoans: number;
  totalBorrowed: number;
  nextEmi: number;
  creditScore: number;
}

export interface NetWorthPoint {
  month: string;
  value: number;
}