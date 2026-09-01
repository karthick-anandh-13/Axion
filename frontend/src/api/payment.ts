import type { Investment, PaymentState, UserType } from "../types/payment";

// Mock data for testing
const MOCK_INVESTMENT: Investment = {
  id: "INV-MOCK-001",
  loanId: "AX-002",
  borrowerName: "Rahul Kumar",
  loanPurpose: "Business Expansion",
  amount: 850000,
  apr: 13.8,
  tenure: 36,
  platformFee: 0,
  gst: 0,
  totalAmount: 850000,
  aiRiskScore: 82,
};

const REAL_INVESTMENT: Investment = {
  id: "INV-REAL-001",
  loanId: "AX-002",
  borrowerName: "Rahul Kumar",
  loanPurpose: "Business Expansion",
  amount: 850000,
  apr: 13.8,
  tenure: 36,
  platformFee: 4250,
  gst: 765,
  totalAmount: 855015,
  aiRiskScore: 82,
};

export const paymentApi = {
  // Get investment details
  getInvestmentDetails: async (
    loanId: string,
    userType: UserType
  ): Promise<Investment> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const investment =
          userType === "mock" ? MOCK_INVESTMENT : REAL_INVESTMENT;
        resolve({ ...investment, loanId });
      }, 500);
    });
  },

  // Process payment
  processPayment: async (
    _investment: Investment,
    userType: UserType
  ): Promise<{ investmentId: string; success: boolean }> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve({
          investmentId: `${userType === "mock" ? "INV-MOCK" : "INV-REAL"}-${Date.now()}`,
          success: true,
        });
      }, 3000);
    });
  },

  // Get payment status
  getPaymentStatus: async (investmentId: string): Promise<PaymentState> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve({
          userType: "mock",
          investment: MOCK_INVESTMENT,
          status: "success",
          investmentId,
          timestamp: new Date().toLocaleString("en-IN"),
        });
      }, 500);
    });
  },

  // Verify investment
  verifyInvestment: async (_investmentId: string): Promise<boolean> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(true);
      }, 1000);
    });
  },
};
