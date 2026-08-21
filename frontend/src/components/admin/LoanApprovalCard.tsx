import GlassCard from "../ui/GlassCard";
import { Check, X } from "lucide-react";
import { motion } from "framer-motion";

interface Props {
  name: string;
  amount: string;
  score: number;
}

export default function LoanApprovalCard({
  name,
  amount,
  score,
}: Props) {
  return (
    <motion.div
      initial={{ opacity: 0, x: 20 }}
      animate={{ opacity: 1, x: 0 }}
    >
      <GlassCard className="p-5">
        <div className="flex items-center justify-between">
          <div>
            <h3 className="text-lg text-white">{name}</h3>
            <p className="text-sm text-white/40">{amount}</p>
          </div>

          <div className="rounded-full bg-[#C7F5D9]/10 px-3 py-1 text-[#C7F5D9]">
            {score}
          </div>
        </div>

        <div className="mt-5 flex gap-3">
          <button className="flex-1 rounded-xl bg-[#C7F5D9] py-3 font-medium text-black">
            <Check size={18} className="mx-auto" />
          </button>

          <button className="flex-1 rounded-xl border border-white/10 bg-white/5 py-3 text-white">
            <X size={18} className="mx-auto" />
          </button>
        </div>
      </GlassCard>
    </motion.div>
  );
}