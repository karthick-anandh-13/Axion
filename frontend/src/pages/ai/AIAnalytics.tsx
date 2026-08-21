import DashboardLayout from "../../layouts/DashboardLayout";
import GlassCard from "../../components/ui/GlassCard";
import { Brain, ShieldCheck, TrendingUp } from "lucide-react";
import { motion } from "framer-motion";

const metrics = [
  { title: "Repayment Probability", value: 94, color: "#C7F5D9" },
  { title: "Financial Stability", value: 88, color: "#F6E7C8" },
  { title: "Fraud Risk", value: 8, color: "#9CA3AF" },
];

const recommendations = [
  "Increase emergency fund by ₹50,000",
  "Your lending allocation can grow 12%",
  "Maintain EMI below 30% of income",
];

export default function AIAnalytics() {
  return (
    <DashboardLayout>
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
      >
        <div className="mb-8">
          <p className="text-white/40">Artificial Intelligence</p>
          <h1 className="text-5xl font-light text-white">
            Intelligence Hub
          </h1>
        </div>

        <div className="grid gap-6 lg:grid-cols-3">
          {/* Trust Score */}
          <GlassCard className="relative overflow-hidden p-6">
            <div className="absolute -top-10 -right-10 h-32 w-32 rounded-full bg-[#C7F5D9]/10 blur-3xl" />

            <div className="flex justify-center">
              <div className="relative h-40 w-40">
                <svg viewBox="0 0 120 120">
                  <circle
                    cx="60"
                    cy="60"
                    r="48"
                    fill="none"
                    stroke="rgba(255,255,255,0.08)"
                    strokeWidth="8"
                  />

                  <motion.circle
                    cx="60"
                    cy="60"
                    r="48"
                    fill="none"
                    stroke="#C7F5D9"
                    strokeWidth="8"
                    strokeLinecap="round"
                    pathLength={100}
                    strokeDasharray="92 8"
                    transform="rotate(-90 60 60)"
                    initial={{ pathLength: 0 }}
                    animate={{ pathLength: 1 }}
                    transition={{ duration: 1.5 }}
                  />
                </svg>

                <div className="absolute inset-0 flex flex-col items-center justify-center">
                  <h2 className="text-4xl font-light text-[#F6E7C8]">
                    92
                  </h2>
                  <p className="text-xs text-white/40">Trust Score</p>
                </div>
              </div>
            </div>
          </GlassCard>

          {/* Predictive Analysis */}
          <GlassCard className="lg:col-span-2 p-6">
            <div className="mb-6 flex items-center gap-3">
              <Brain className="text-[#F6E7C8]" />
              <h3 className="text-xl text-white">Predictive Analysis</h3>
            </div>

            {metrics.map((item) => (
              <div key={item.title} className="mb-5">
                <div className="mb-2 flex justify-between text-sm">
                  <span className="text-white/50">{item.title}</span>
                  <span style={{ color: item.color }}>{item.value}%</span>
                </div>

                <div className="h-2 rounded-full bg-white/10">
                  <motion.div
                    initial={{ width: 0 }}
                    animate={{ width: `${item.value}%` }}
                    transition={{ duration: 1 }}
                    className="h-full rounded-full"
                    style={{ background: item.color }}
                  />
                </div>
              </div>
            ))}
          </GlassCard>
        </div>

        <div className="mt-6 grid gap-6 lg:grid-cols-2">
          {/* AI Recommendations */}
          <GlassCard className="p-6">
            <div className="mb-5 flex items-center gap-2">
              <TrendingUp className="text-[#C7F5D9]" />
              <h3 className="text-lg text-white">AI Recommendations</h3>
            </div>

            <div className="space-y-3">
              {recommendations.map((tip, index) => (
                <motion.div
                  key={tip}
                  initial={{ opacity: 0, x: 20 }}
                  animate={{ opacity: 1, x: 0 }}
                  transition={{ delay: index * 0.15 }}
                  className="rounded-xl bg-white/5 p-4 text-white/80"
                >
                  {tip}
                </motion.div>
              ))}
            </div>
          </GlassCard>

          {/* Explainable AI */}
          <GlassCard className="p-6">
            <div className="mb-5 flex items-center gap-2">
              <ShieldCheck className="text-[#F6E7C8]" />
              <h3 className="text-lg text-white">Explainable AI</h3>
            </div>

            <div className="space-y-4 text-sm">
              {[
                ["Income Consistency", "Excellent", "#C7F5D9"],
                ["Repayment History", "Strong", "#C7F5D9"],
                ["Spending Discipline", "Good", "#F6E7C8"],
              ].map(([label, status, color]) => (
                <div key={label} className="flex justify-between">
                  <span className="text-white/50">{label}</span>
                  <span style={{ color }}>{status}</span>
                </div>
              ))}

              <div className="mt-6 rounded-xl bg-white/5 p-4 text-white/70">
                Your AI score is primarily driven by stable income,
                consistent repayments, and low debt utilization.
              </div>
            </div>
          </GlassCard>
        </div>
      </motion.div>
    </DashboardLayout>
  );
}