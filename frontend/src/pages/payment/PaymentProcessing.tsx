import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import DashboardLayout from "../../layouts/DashboardLayout";
import ProgressTimeline from "../../components/payment/ProgressTimeline";
import TransferAnimation from "../../components/payment/TransferAnimation";
import { paymentApi } from "../../api/payment";
import type { Investment, UserType } from "../../types/payment";

export default function PaymentProcessing() {
  const navigate = useNavigate();
  const { investmentId, userType = "mock" } = useParams<{
    investmentId: string;
    userType: UserType;
  }>();

  const [investment, setInvestment] = useState<Investment | null>(null);
  const [loading, setLoading] = useState(true);
  const [processingStep, setProcessingStep] = useState(0);

  const steps = [
    { id: "1", label: "Review", completed: true, current: false },
    { id: "2", label: "Processing", completed: false, current: true },
    { id: "3", label: "Transferring", completed: false, current: false },
    { id: "4", label: "Success", completed: false, current: false },
    { id: "5", label: "Confirmation", completed: false, current: false },
    { id: "6", label: "Portfolio", completed: false, current: false },
  ];

  useEffect(() => {
    if (investmentId) {
      // Simulate getting investment details from previous step
      paymentApi
        .getInvestmentDetails(investmentId, userType || "mock")
        .then(setInvestment)
        .finally(() => setLoading(false));
    }
  }, [investmentId, userType]);

  useEffect(() => {
    // Simulate processing steps
    const timer1 = setTimeout(() => setProcessingStep(1), 2000);
    const timer2 = setTimeout(() => {
      navigate(`/payment/success/${investmentId}/${userType}`);
    }, 5000);

    return () => {
      clearTimeout(timer1);
      clearTimeout(timer2);
    };
  }, [investmentId, userType, navigate]);

  if (loading || !investment) {
    return (
      <DashboardLayout>
        <div className="flex items-center justify-center py-20">
          <p className="text-white/60">Loading payment details...</p>
        </div>
      </DashboardLayout>
    );
  }

  return (
    <DashboardLayout>
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        className="max-w-3xl space-y-8"
      >
        {/* Header */}
        <div>
          <h1 className="text-3xl font-light text-white">
            Processing Your Investment
          </h1>
          <p className="mt-1 text-sm text-white/40">
            Step 3 of 6 • Payment Processing
          </p>
        </div>

        {/* Progress Timeline */}
        <ProgressTimeline steps={steps} />

        {/* Processing Animation */}
        <motion.div
          initial={{ opacity: 0, scale: 0.95 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ delay: 0.2 }}
          className="rounded-3xl border border-white/10 bg-white/5 p-8 backdrop-blur-xl"
        >
          {/* Circular Progress */}
          <div className="mb-8 flex justify-center">
            <motion.div
              animate={{ rotate: 360 }}
              transition={{ duration: 3, repeat: Infinity, ease: "linear" }}
              className="relative h-40 w-40"
            >
              <svg
                className="absolute inset-0 h-full w-full"
                viewBox="0 0 100 100"
              >
                <circle
                  cx="50"
                  cy="50"
                  r="45"
                  fill="none"
                  stroke="rgba(255, 255, 255, 0.1)"
                  strokeWidth="2"
                />
                <motion.circle
                  cx="50"
                  cy="50"
                  r="45"
                  fill="none"
                  stroke="url(#grad1)"
                  strokeWidth="2"
                  strokeDasharray="141 283"
                  strokeLinecap="round"
                  animate={{ strokeDashoffset: [0, -283] }}
                  transition={{ duration: 2, repeat: Infinity, ease: "linear" }}
                />
                <defs>
                  <linearGradient id="grad1" x1="0%" y1="0%" x2="100%">
                    <stop offset="0%" stopColor="#C7F5D9" />
                    <stop offset="100%" stopColor="#F6E7C8" />
                  </linearGradient>
                </defs>
              </svg>

              {/* Center text */}
              <div className="absolute inset-0 flex flex-col items-center justify-center">
                <p className="text-2xl font-light text-[#F6E7C8]">
                  ₹{new Intl.NumberFormat("en-IN").format(
                    investment.totalAmount
                  )}
                </p>
                <p className="mt-1 text-xs text-white/40">Processing...</p>
              </div>
            </motion.div>
          </div>

          {/* Status Messages */}
          <div className="space-y-3 text-center">
            <motion.p
              key={processingStep}
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              className="text-base font-medium text-white"
            >
              {processingStep === 0
                ? "Verifying your details..."
                : "Processing payment..."}
            </motion.p>
            <p className="text-sm text-white/60">
              This should take only a few seconds. Please do not close this window.
            </p>
          </div>
        </motion.div>

        {/* Transfer Animation */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.4 }}
          className="rounded-3xl border border-white/10 bg-white/5 p-8 backdrop-blur-xl"
        >
          <p className="mb-6 text-center text-sm text-white/60">
            Transferring funds to {investment.borrowerName}
          </p>
          <TransferAnimation
            fromLabel="Your Wallet"
            toLabel={investment.borrowerName}
            isAnimating={true}
          />
        </motion.div>
      </motion.div>
    </DashboardLayout>
  );
}
