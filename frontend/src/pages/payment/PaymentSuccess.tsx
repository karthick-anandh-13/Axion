import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { Check, Copy, Download } from "lucide-react";
import DashboardLayout from "../../layouts/DashboardLayout";
import ProgressTimeline from "../../components/payment/ProgressTimeline";
import MoneyParticles from "../../components/payment/MoneyParticles";
import { paymentApi } from "../../api/payment";
import type { UserType } from "../../types/payment";

export default function PaymentSuccess() {
  const navigate = useNavigate();
  const { investmentId, userType: _userType = "mock" } = useParams<{
    investmentId: string;
    userType: UserType;
  }>();

  const [paymentStatus, setPaymentStatus] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const [copied, setCopied] = useState(false);

  const steps = [
    { id: "1", label: "Review", completed: true, current: false },
    { id: "2", label: "Processing", completed: true, current: false },
    { id: "3", label: "Transferring", completed: true, current: false },
    { id: "4", label: "Success", completed: true, current: false },
    { id: "5", label: "Confirmation", completed: false, current: true },
    { id: "6", label: "Portfolio", completed: false, current: false },
  ];

  useEffect(() => {
    if (investmentId) {
      paymentApi
        .getPaymentStatus(investmentId)
        .then(setPaymentStatus)
        .finally(() => setLoading(false));
    }
  }, [investmentId]);

  const handleCopyId = () => {
    if (paymentStatus?.investmentId) {
      navigator.clipboard.writeText(paymentStatus.investmentId);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    }
  };

  const handleContinue = () => {
    navigate("/dashboard");
  };

  if (loading || !paymentStatus) {
    return (
      <DashboardLayout>
        <div className="flex items-center justify-center py-20">
          <p className="text-white/60">Confirming your investment...</p>
        </div>
      </DashboardLayout>
    );
  }

  const investment = paymentStatus.investment;

  return (
    <DashboardLayout>
      <MoneyParticles isActive={true} />

      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        className="max-w-3xl space-y-8"
      >
        {/* Header */}
        <div>
          <h1 className="text-3xl font-light text-white">
            Investment Successful!
          </h1>
          <p className="mt-1 text-sm text-white/40">
            Step 5 of 6 • Payment Confirmation
          </p>
        </div>

        {/* Progress Timeline */}
        <ProgressTimeline steps={steps} />

        {/* Success Card */}
        <motion.div
          initial={{ scale: 0.95, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          transition={{ delay: 0.2 }}
          className="rounded-3xl border border-green-500/20 bg-green-500/5 p-8 backdrop-blur-xl"
        >
          {/* Success Checkmark */}
          <motion.div
            initial={{ scale: 0 }}
            animate={{ scale: 1 }}
            transition={{ delay: 0.3, type: "spring" }}
            className="mx-auto mb-8 flex h-24 w-24 items-center justify-center rounded-full bg-green-500/20"
          >
            <Check size={48} className="text-green-400" />
          </motion.div>

          {/* Investment Details */}
          <div className="mb-8 space-y-4 text-center">
            <h2 className="text-2xl font-light text-white">
              ₹{new Intl.NumberFormat("en-IN").format(investment.totalAmount)}
            </h2>
            <p className="text-white/60">invested successfully</p>
            <div className="flex items-center justify-center gap-2 text-sm text-white/40">
              <span>{investment.borrowerName}</span>
              <span>•</span>
              <span>{investment.loanPurpose}</span>
            </div>
          </div>

          {/* Details Grid */}
          <div className="mb-8 space-y-3 border-t border-white/10 pt-8">
            <div className="flex items-center justify-between">
              <span className="text-white/60">Investment ID</span>
              <div className="flex items-center gap-2">
                <code className="text-sm font-mono text-[#F6E7C8]">
                  {paymentStatus.investmentId}
                </code>
                <button
                  onClick={handleCopyId}
                  className="rounded p-1 hover:bg-white/10"
                  title="Copy Investment ID"
                >
                  <Copy size={16} className="text-white/40" />
                </button>
              </div>
            </div>

            {copied && (
              <motion.p
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                className="text-center text-sm text-green-400"
              >
                ✓ Copied to clipboard
              </motion.p>
            )}

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

            <div className="flex items-center justify-between">
              <span className="text-white/60">Timestamp</span>
              <span className="font-light text-white">
                {paymentStatus.timestamp}
              </span>
            </div>
          </div>

          {/* Security Badge */}
          <div className="flex items-center gap-3 rounded-lg bg-green-500/10 p-4">
            <Check size={20} className="text-green-400" />
            <div>
              <p className="text-sm font-medium text-green-300">
                Investment Confirmed
              </p>
              <p className="text-xs text-green-300/60">
                A confirmation email has been sent to your registered email
              </p>
            </div>
          </div>
        </motion.div>

        {/* Action Buttons */}
        <div className="flex gap-3">
          <button
            className="flex-1 rounded-xl border border-white/10 px-6 py-3 font-medium text-white transition hover:bg-white/5"
            onClick={() => window.print()}
          >
            <Download size={18} className="mx-auto" />
          </button>
          <button
            onClick={handleContinue}
            className="flex-1 rounded-xl bg-gradient-to-r from-[#C7F5D9] to-[#F6E7C8] px-6 py-3 font-semibold text-black transition hover:shadow-lg hover:shadow-[#C7F5D9]/25"
          >
            Go to Portfolio
          </button>
        </div>
      </motion.div>
    </DashboardLayout>
  );
}
