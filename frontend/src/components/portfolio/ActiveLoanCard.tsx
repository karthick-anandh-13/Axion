import { motion } from "framer-motion";
import GlassCard from "../ui/GlassCard";
import ProgressRing from "./ProgressRing";
import { CalendarDays } from "lucide-react";

interface Props {
  title: string;
  principal: number;
  emi: number;
  progress: number;
  nextDue: string;
}

export default function ActiveLoanCard({
  title,
  principal,
  emi,
  progress,
  nextDue,
}: Props) {
  return (
    <motion.div
      whileHover={{ y: -8 }}
      transition={{ duration: 0.25 }}
      className="min-w-290px"
    >
      <GlassCard className="p-5 h-full">
        <div className="flex items-center justify-between">
          <ProgressRing progress={progress} />

          <span className="rounded-full bg-[#C7F5D9]/10 px-3 py-1 text-xs text-[#C7F5D9]">
            Active
          </span>
        </div>

        <h3 className="mt-5 text-xl font-light text-white">
          {title}
        </h3>

        <p className="mt-1 text-sm text-white/40">
          ₹{new Intl.NumberFormat("en-IN").format(principal)}
        </p>

        <div className="mt-6 h-2 rounded-full bg-white/10">
          <motion.div
            initial={{ width: 0 }}
            animate={{ width: `${progress}%` }}
            transition={{ duration: 1.2 }}
            className="h-full rounded-full bg-linear-to-r from-[#C7F5D9] to-[#F6E7C8]"
          />
        </div>

        <div className="mt-6 flex items-center justify-between">
          <div>
            <p className="text-xs text-white/40">
              Next EMI
            </p>

            <h4 className="text-lg font-light text-[#F6E7C8]">
              ₹{new Intl.NumberFormat("en-IN").format(emi)}
            </h4>
          </div>

          <div className="flex items-center gap-2 text-xs text-white/50">
            <CalendarDays size={14} />
            {nextDue}
          </div>
        </div>
      </GlassCard>
    </motion.div>
  );
}