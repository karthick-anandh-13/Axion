import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { ArrowLeft, Check } from "lucide-react";
import DashboardLayout from "../../layouts/DashboardLayout";
import { paymentApi } from "../../api/payment";
import type { Investment, UserType } from "../../types/payment";

export default function PaymentSummary() {
  const navigate = useNavigate();
  const { id, userType = "mock" } = useParams<{
    id: string;
    userType: UserType;
  }>();

  const [investment, setInvestment] = useState<Investment | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (id) {
      paymentApi
        .getInvestmentDetails(id, userType || "mock")
        .then(setInvestment)
        .finally(() => setLoading(false));
    }
  }, [id, userType]);

  if (loading || !investment) {
    return (
      <DashboardLayout>
        <div className="flex items-center justify-center py-20">
          <p className="text-white/60">Loading investment details...</p>
        </div>
      </DashboardLayout>
    );
  }

  const handleProceedToPayment = () => {
    navigate(`/payment/processing/${investment.id}/${userType}`);
  };

  return (
    <DashboardLayout>
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        className="max-w-2xl space-y-6"
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
            <h1 className="text-3xl font-light text-white">
              Review Your Investment
            </h1>
            <p className="mt-1 text-sm text-white/40">
              Step 2 of 6 • Payment Summary
            </p>
          </div>
        </div>

        {/* Investment Summary Card */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.1 }}
          className="rounded-3xl border border-white/10 bg-white/5 p-8 backdrop-blur-xl"
        >
          <div className="mb-6 grid grid-cols-2 gap-6">
            <div>
              <p className="text-xs text-white/40 uppercase tracking-wide">
                Borrower
              </p>
              <h3 className="mt-2 text-xl font-light text-white">
                {investment.borrowerName}
              </h3>
            </div>
            <div>
              <p className="text-xs text-white/40 uppercase tracking-wide">
                Loan Purpose
              </p>
              <h3 className="mt-2 text-xl font-light text-white">
                {investment.loanPurpose}
              </h3>
            </div>
          </div>

          {/* Details Grid */}
          <div className="mb-6 space-y-4 border-y border-white/10 py-6">
            <div className="flex items-center justify-between">
              <span className="text-white/60">Investment Amount</span>
              <span className="text-lg font-light text-[#F6E7C8]">
                ₹{new Intl.NumberFormat("en-IN").format(investment.amount)}
              </span>
            </div>

            <div className="flex items-center justify-between">
              <span className="text-white/60">APR</span>
              <span className="font-light text-white">{investment.apr}%</span>
            </div>

            <div className="flex items-center justify-between">
              <span className="text-white/60">Tenure</span>
              <span className="font-light text-white">
                {investment.tenure} Months
              </span>
            </div>

            {userType === "real" && (
              <>
                <div className="flex items-center justify-between">
                  <span className="text-white/60">Platform Fee</span>
                  <span className="font-light text-white">
                    ₹{new Intl.NumberFormat("en-IN").format(
                      investment.platformFee
                    )}
                  </span>
                </div>

                <div className="flex items-center justify-between">
                  <span className="text-white/60">GST (0%)</span>
                  <span className="font-light text-white">
                    ₹{new Intl.NumberFormat("en-IN").format(investment.gst)}
                  </span>
                </div>
              </>
            )}
          </div>

          {/* Total Amount */}
          <div className="mb-6 rounded-xl bg-gradient-to-r from-[#C7F5D9]/10 to-[#F6E7C8]/10 p-4">
            <p className="text-xs text-white/40 uppercase tracking-wide">
              Total Amount
            </p>
            <h2 className="mt-2 text-3xl font-light text-[#F6E7C8]">
              ₹{new Intl.NumberFormat("en-IN").format(investment.totalAmount)}
            </h2>
          </div>

          {/* Security Badge */}
          <div className="flex items-center gap-3 rounded-lg border border-green-500/20 bg-green-500/5 p-4">
            <Check size={20} className="text-green-400" />
            <div>
              <p className="text-sm font-medium text-green-300">Secure & Safe</p>
              <p className="text-xs text-green-300/60">
                Your investment is protected with bank-grade security
              </p>
            </div>
          </div>
        </motion.div>

        {/* Action Buttons */}
        <div className="flex gap-3">
          <button
            onClick={() => navigate(-1)}
            className="flex-1 rounded-xl border border-white/10 px-6 py-3 font-medium text-white transition hover:bg-white/5"
          >
            Back
          </button>
          <button
            onClick={handleProceedToPayment}
            className="flex-1 rounded-xl bg-gradient-to-r from-[#C7F5D9] to-[#F6E7C8] px-6 py-3 font-semibold text-black transition hover:shadow-lg hover:shadow-[#C7F5D9]/25"
          >
            Proceed to Pay ₹{new Intl.NumberFormat("en-IN").format(
              investment.totalAmount
            )}
          </button>
        </div>
      </motion.div>
    </DashboardLayout>
  );
}
