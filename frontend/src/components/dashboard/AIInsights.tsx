import { motion } from "framer-motion";
import GlassCard from "../ui/GlassCard";
import { Brain, ShieldCheck, TrendingUp, AlertTriangle } from "lucide-react";

const insights = [
  {
    icon: TrendingUp,
    title: "Increase lending allocation",
    desc: "Low risk · Potential +12% annual return",
    color: "text-[#C7F5D9]",
  },
  {
    icon: AlertTriangle,
    title: "2 borrowers require review",
    desc: "Medium priority · Due within 48 hours",
    color: "text-[#FFD98A]",
  },
  {
    icon: ShieldCheck,
    title: "Emergency fund is healthy",
    desc: "AI verified · 6.4 months coverage",
    color: "text-[#C7F5D9]",
  },
];

export default function AIInsights() {
  return (
    <GlassCard className="p-6 h-full">
      <div className="mb-6 flex items-center gap-3">
        <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-[#F6E7C8]/10">
          <Brain className="text-[#F6E7C8]" />
        </div>

        <div>
          <p className="text-sm text-white/40">AXION AI</p>
          <h3 className="text-xl text-white">Financial Copilot</h3>
        </div>
      </div>

      <div className="mb-8 rounded-2xl bg-white/5 p-5">
        <p className="text-sm text-white/40">Trust Score</p>

        <div className="mt-2 flex items-end gap-2">
          <motion.h2
            initial={{ opacity: 0, y: 8 }}
            animate={{ opacity: 1, y: 0 }}
            className="text-5xl font-light text-[#F6E7C8]"
          >
            98
          </motion.h2>

          <span className="mb-2 text-[#C7F5D9]">Excellent</span>
        </div>
      </div>

      <div className="space-y-3">
        {insights.map((item, i) => {
          const Icon = item.icon;

          return (
            <motion.div
              key={item.title}
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: i * 0.15 }}
              className="rounded-xl border border-white/5 bg-white/[0.03] p-4"
            >
              <div className="flex gap-3">
                <Icon className={item.color} size={20} />

                <div>
                  <h4 className="text-sm text-white">
                    {item.title}
                  </h4>
                  <p className="mt-1 text-xs text-white/40">
                    {item.desc}
                  </p>
                </div>
              </div>
            </motion.div>
          );
        })}
      </div>
    </GlassCard>
  );
}