import { motion } from "framer-motion";

interface RiskBadgeProps {
  score: number;
}

export function RiskBadge({ score }: RiskBadgeProps) {
  const getRisk = () => {
    if (score >= 85) {
      return {
        label: "LOW RISK",
        color: "text-emerald-300",
        bg: "bg-emerald-500/15",
        border: "border-emerald-400/30",
      };
    }

    if (score >= 70) {
      return {
        label: "MEDIUM",
        color: "text-amber-300",
        bg: "bg-amber-500/15",
        border: "border-amber-400/30",
      };
    }

    return {
      label: "HIGH RISK",
      color: "text-red-300",
      bg: "bg-red-500/15",
      border: "border-red-400/30",
    };
  };

  const risk = getRisk();

  return (
    <motion.div
      initial={{ scale: 0.9, opacity: 0 }}
      animate={{ scale: 1, opacity: 1 }}
      transition={{ duration: 0.25 }}
      className={`rounded-xl border px-3 py-2 ${risk.bg} ${risk.border}`}
    >
      <p className="text-[10px] tracking-widest text-white/50">AI SCORE</p>

      <div className="mt-1 flex items-end gap-2">
        <span className="text-xl font-semibold text-white">{score}</span>
        <span className={`text-xs font-medium ${risk.color}`}>
          {risk.label}
        </span>
      </div>
    </motion.div>
  );
}