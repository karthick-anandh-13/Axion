import { useMemo, useState } from "react";
import { AnimatePresence, motion } from "framer-motion";
import {
  IndianRupee,
  Sparkles,
  Check,
  Loader2,
} from "lucide-react";

import DashboardLayout from "../../layouts/DashboardLayout";
import GlassCard from "../../components/ui/GlassCard";
import PrimaryButton from "../../components/ui/PrimaryButton";
import { createLoan } from "../../api/loan";

export default function Borrow() {
  const [amount, setAmount] = useState(850000);
  const [months, setMonths] = useState(24);
  const [purpose, setPurpose] = useState("");

  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);

  const interest = 0.11 / 12;

  const emi = useMemo(() => {
    const r = interest;
    const n = months;

    const value =
      (amount * r * Math.pow(1 + r, n)) /
      (Math.pow(1 + r, n) - 1);

    return Math.round(value);
  }, [amount, months]);

  const approval = Math.min(
    99,
    Math.max(72, 96 - Math.floor(amount / 400000))
  );

  const handleSubmit = async () => {
    if (!purpose.trim()) {
      alert("Please enter the purpose of the loan.");
      return;
    }

    try {
      setLoading(true);

      await createLoan({
        amount,
        tenureMonths: months,
        purpose,
      });

      setSuccess(true);
      setPurpose("");
    } catch (error) {
      console.error(error);
      alert("Unable to submit your application.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <DashboardLayout>
      <div className="mb-8">
        <p className="text-white/40">Borrow</p>
        <h1 className="text-5xl font-light text-white">
          Intelligent Loans
        </h1>
      </div>

      <div className="grid gap-6 lg:grid-cols-3">
        <GlassCard className="lg:col-span-2 p-8">
          <div className="mb-8 flex items-center gap-3">
            <IndianRupee className="text-[#F6E7C8]" />
            <p className="text-white/50">Loan Amount</p>
          </div>

          <h2 className="text-5xl font-light text-[#F6E7C8]">
            ₹{new Intl.NumberFormat("en-IN").format(amount)}
          </h2>

          <input
            type="range"
            min={50000}
            max={5000000}
            step={10000}
            value={amount}
            onChange={(e) => setAmount(Number(e.target.value))}
            className="mt-10 h-2 w-full appearance-none rounded-full bg-white/10 accent-[#C7F5D9]"
          />

          <div className="mt-10">
            <p className="mb-4 text-white/50">Duration</p>

            <div className="grid grid-cols-4 gap-3">
              {[12, 24, 36, 60].map((m) => (
                <button
                  key={m}
                  onClick={() => setMonths(m)}
                  className={`rounded-2xl py-3 transition-all ${
                    months === m
                      ? "border border-[#C7F5D9]/40 bg-[#C7F5D9]/10 text-[#C7F5D9]"
                      : "border border-white/10 bg-white/5 text-white/50"
                  }`}
                >
                  {m}m
                </button>
              ))}
            </div>
          </div>

          <div className="mt-10">
            <p className="mb-4 text-white/50">Purpose</p>

            <textarea
              rows={5}
              value={purpose}
              onChange={(e) => setPurpose(e.target.value)}
              placeholder="Example: Business expansion, education, medical expenses..."
              className="w-full resize-none rounded-2xl border border-white/10 bg-white/5 p-4 text-white placeholder:text-white/25 outline-none transition focus:border-[#C7F5D9]/40"
            />
          </div>
        </GlassCard>

        <GlassCard className="p-6">
          <div className="mb-5 flex items-center gap-3">
            <Sparkles className="text-[#F6E7C8]" />
            <h3 className="text-lg text-white">AI Eligibility</h3>
          </div>

          <div className="mb-6">
            <div className="flex items-end gap-2">
              <h2 className="text-5xl font-light text-[#C7F5D9]">
                {approval}
              </h2>
              <span className="mb-2 text-white">%</span>
            </div>

            <p className="text-sm text-white/50">
              Approval Probability
            </p>
          </div>

          <div className="mb-6 h-3 overflow-hidden rounded-full bg-white/10">
            <motion.div
              animate={{ width: `${approval}%` }}
              transition={{ duration: 0.8 }}
              className="h-full rounded-full bg-linear-to-r from-[#C7F5D9] to-[#F6E7C8]"
            />
          </div>

          <div className="space-y-4 text-sm">
            <div className="flex justify-between">
              <span className="text-white/40">Interest</span>
              <span className="text-white">11%</span>
            </div>

            <div className="flex justify-between">
              <span className="text-white/40">Monthly EMI</span>
              <span className="text-[#F6E7C8]">
                ₹{new Intl.NumberFormat("en-IN").format(emi)}
              </span>
            </div>

            <div className="flex justify-between">
              <span className="text-white/40">Tenure</span>
              <span className="text-white">{months} months</span>
            </div>
          </div>

          <div className="mt-8">
            <PrimaryButton
              disabled={loading}
              onClick={handleSubmit}
            >
              {loading ? (
                <div className="flex items-center justify-center gap-2">
                  <Loader2 size={18} className="animate-spin" />
                  Submitting...
                </div>
              ) : (
                "Apply Now"
              )}
            </PrimaryButton>
          </div>
        </GlassCard>
      </div>

      <AnimatePresence>
        {success && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-xl"
          >
            <motion.div
              initial={{ scale: 0.85, y: 40 }}
              animate={{ scale: 1, y: 0 }}
              transition={{
                type: "spring",
                stiffness: 140,
                damping: 18,
              }}
            >
              <GlassCard className="w-420px p-10">
                <div className="flex flex-col items-center text-center">
                  <motion.div
                    initial={{ scale: 0 }}
                    animate={{ scale: 1 }}
                    transition={{
                      delay: 0.2,
                      type: "spring",
                    }}
                    className="mb-6 flex h-24 w-24 items-center justify-center rounded-full bg-[#C7F5D9]/10"
                  >
                    <Check
                      size={46}
                      className="text-[#C7F5D9]"
                    />
                  </motion.div>

                  <h2 className="text-3xl font-light text-white">
                    Application Submitted
                  </h2>

                  <p className="mt-3 text-sm text-white/50">
                    Your loan request has entered AXION's AI underwriting pipeline.
                  </p>

                  <div className="mt-6 space-y-3 text-sm">
                    <div className="flex items-center gap-2 text-white/80">
                      <Check size={16} className="text-[#C7F5D9]" />
                      Identity verified
                    </div>

                    <div className="flex items-center gap-2 text-white/80">
                      <Check size={16} className="text-[#C7F5D9]" />
                      Credit evaluation started
                    </div>

                    <div className="flex items-center gap-2 text-white/80">
                      <Check size={16} className="text-[#C7F5D9]" />
                      Estimated review: 2–5 minutes
                    </div>
                  </div>

                  <div className="mt-8 w-full">
                    <PrimaryButton
                      onClick={() => setSuccess(false)}
                    >
                      Continue
                    </PrimaryButton>
                  </div>
                </div>
              </GlassCard>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </DashboardLayout>
  );
}