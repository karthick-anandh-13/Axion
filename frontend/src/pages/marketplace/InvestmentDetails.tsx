import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { ArrowLeft, Shield, TrendingUp, Calendar, User } from "lucide-react";
import DashboardLayout from "../../layouts/DashboardLayout";

// Sample loan data - in a real app, this would come from the API
const LOAN_DATA: Record<
  string,
  {
    id: string;
    borrowerName: string;
    borrowerAvatar: string;
    loanPurpose: string;
    amount: number;
    apr: number;
    tenure: number;
    aiRiskScore: number;
    description: string;
    monthlyReturn: number;
    totalReturn: number;
  }
> = {
  AX002: {
    id: "AX-002",
    borrowerName: "Rahul Kumar",
    borrowerAvatar: "👨‍💼",
    loanPurpose: "Business Expansion",
    amount: 850000,
    apr: 13.8,
    tenure: 36,
    aiRiskScore: 82,
    description:
      "Rahul Kumar is seeking funds to expand his successful retail business to a new location. Strong credit history and 5 years of successful business operations.",
    monthlyReturn: 9872,
    totalReturn: 355000,
  },
};

export default function InvestmentDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [userType] = useState<"mock" | "real">("mock");

  const loanId = id || "AX002";
  const loan = LOAN_DATA[loanId] || LOAN_DATA["AX002"];

  const handleConfirmInvestment = () => {
    navigate(`/payment/summary/${loanId}/${userType}`);
  };

  return (
    <DashboardLayout>
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        className="max-w-4xl space-y-6"
      >
        {/* Header */}
        <div className="flex items-center gap-4">
          <button
            onClick={() => navigate(-1)}
            className="rounded-lg border border-white/10 p-2 hover:bg-white/5"
          >
            <ArrowLeft size={20} className="text-white" />
          </button>
          <div>
            <p className="text-sm text-white/40">Investment Opportunity</p>
            <h1 className="mt-1 text-3xl font-light text-white">
              {loan.loanPurpose}
            </h1>
          </div>
        </div>

        {/* Main Content Grid */}
        <div className="grid gap-6 lg:grid-cols-3">
          {/* Left Column - Borrower & Details */}
          <div className="lg:col-span-2 space-y-6">
            {/* Borrower Card */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.1 }}
              className="rounded-3xl border border-white/10 bg-white/5 p-8 backdrop-blur-xl"
            >
              <div className="flex items-center gap-4">
                <div className="flex h-16 w-16 items-center justify-center rounded-full bg-gradient-to-br from-[#C7F5D9] to-[#F6E7C8] text-4xl">
                  {loan.borrowerAvatar}
                </div>
                <div>
                  <h2 className="text-2xl font-light text-white">
                    {loan.borrowerName}
                  </h2>
                  <p className="text-sm text-white/40">Verified Borrower</p>
                </div>
              </div>

              <p className="mt-6 leading-relaxed text-white/70">
                {loan.description}
              </p>
            </motion.div>

            {/* Investment Details */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.2 }}
              className="rounded-3xl border border-white/10 bg-white/5 p-8 backdrop-blur-xl"
            >
              <h3 className="mb-6 text-lg font-medium text-white">
                Investment Details
              </h3>

              <div className="grid gap-6 md:grid-cols-2">
                {/* Amount */}
                <div>
                  <p className="text-xs text-white/40 uppercase tracking-wide">
                    Loan Amount
                  </p>
                  <h4 className="mt-2 text-3xl font-light text-[#F6E7C8]">
                    ₹{new Intl.NumberFormat("en-IN").format(loan.amount)}
                  </h4>
                </div>

                {/* APR */}
                <div>
                  <p className="text-xs text-white/40 uppercase tracking-wide">
                    Annual Percentage Rate
                  </p>
                  <h4 className="mt-2 text-3xl font-light text-white">
                    {loan.apr}%
                  </h4>
                </div>

                {/* Tenure */}
                <div>
                  <p className="text-xs text-white/40 uppercase tracking-wide">
                    Tenure
                  </p>
                  <h4 className="mt-2 text-3xl font-light text-white">
                    {loan.tenure}
                  </h4>
                  <p className="text-sm text-white/40">Months</p>
                </div>

                {/* AI Risk Score */}
                <div>
                  <p className="text-xs text-white/40 uppercase tracking-wide">
                    AI Risk Score
                  </p>
                  <div className="mt-2 flex items-baseline gap-2">
                    <h4 className="text-3xl font-light text-white">
                      {loan.aiRiskScore}
                    </h4>
                    <span className="text-sm text-green-400">/100</span>
                  </div>
                  <p className="text-sm text-white/40">Very Low Risk</p>
                </div>
              </div>
            </motion.div>

            {/* Returns Projection */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.3 }}
              className="rounded-3xl border border-[#C7F5D9]/20 bg-[#C7F5D9]/5 p-8 backdrop-blur-xl"
            >
              <h3 className="mb-6 flex items-center gap-2 text-lg font-medium text-white">
                <TrendingUp size={20} className="text-[#C7F5D9]" />
                Projected Returns
              </h3>

              <div className="grid gap-6 md:grid-cols-2">
                <div>
                  <p className="text-xs text-white/40 uppercase tracking-wide">
                    Monthly Return
                  </p>
                  <h4 className="mt-2 text-2xl font-light text-[#C7F5D9]">
                    ₹{new Intl.NumberFormat("en-IN").format(loan.monthlyReturn)}
                  </h4>
                </div>

                <div>
                  <p className="text-xs text-white/40 uppercase tracking-wide">
                    Total Return (36 Months)
                  </p>
                  <h4 className="mt-2 text-2xl font-light text-[#C7F5D9]">
                    ₹{new Intl.NumberFormat("en-IN").format(loan.totalReturn)}
                  </h4>
                </div>
              </div>
            </motion.div>
          </div>

          {/* Right Column - Risk & Security */}
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.4 }}
            className="space-y-6"
          >
            {/* Risk Assessment Card */}
            <div className="rounded-3xl border border-white/10 bg-white/5 p-8 backdrop-blur-xl">
              <h3 className="mb-4 flex items-center gap-2 text-lg font-medium text-white">
                <Shield size={20} className="text-green-400" />
                Risk Level
              </h3>

              <div className="mb-6 flex items-end gap-2">
                <div className="flex-1 space-y-2">
                  {[...Array(5)].map((_, i) => (
                    <div
                      key={i}
                      className={`h-2 rounded-full transition-all ${
                        i < 4
                          ? "bg-green-500/40"
                          : "bg-green-500 shadow-lg shadow-green-500/25"
                      }`}
                    />
                  ))}
                </div>
              </div>

              <p className="text-center text-sm font-medium text-green-400">
                Very Low Risk
              </p>
              <p className="mt-1 text-center text-xs text-white/40">
                High likelihood of repayment
              </p>
            </div>

            {/* Quick Facts */}
            <div className="rounded-3xl border border-white/10 bg-white/5 p-6 backdrop-blur-xl space-y-4">
              <div className="flex items-start gap-3">
                <User size={18} className="mt-1 text-[#C7F5D9]" />
                <div>
                  <p className="text-xs text-white/40">Borrower Verified</p>
                  <p className="text-sm text-white">✓ Identity Verified</p>
                </div>
              </div>

              <div className="flex items-start gap-3">
                <Calendar size={18} className="mt-1 text-[#C7F5D9]" />
                <div>
                  <p className="text-xs text-white/40">Payment Schedule</p>
                  <p className="text-sm text-white">Monthly EMI</p>
                </div>
              </div>

              <div className="flex items-start gap-3">
                <Shield size={18} className="mt-1 text-[#C7F5D9]" />
                <div>
                  <p className="text-xs text-white/40">Insurance</p>
                  <p className="text-sm text-white">Fully Insured</p>
                </div>
              </div>
            </div>
          </motion.div>
        </div>

        {/* Action Buttons */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.5 }}
          className="flex gap-3"
        >
          <button
            onClick={() => navigate(-1)}
            className="flex-1 rounded-xl border border-white/10 px-6 py-3 font-medium text-white transition hover:bg-white/5"
          >
            Back
          </button>
          <button
            onClick={handleConfirmInvestment}
            className="flex-1 rounded-xl bg-gradient-to-r from-[#C7F5D9] to-[#F6E7C8] px-6 py-3 font-semibold text-black transition hover:shadow-lg hover:shadow-[#C7F5D9]/25"
          >
            Proceed to Investment
          </button>
        </motion.div>
      </motion.div>
    </DashboardLayout>
  );
}