export interface CreditInsight {
  creditScore: number;
  approvalProbability: number;
  recommendation: "APPROVE" | "REVIEW" | "REJECT";

  factors: CreditFactor[];
}

export interface CreditFactor {
  title: string;
  impact: number;
}