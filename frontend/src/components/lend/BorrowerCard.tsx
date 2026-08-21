import { motion } from "framer-motion";
import GlassCard from "../ui/GlassCard";
import { TrendingUp, ShieldCheck } from "lucide-react";

interface Borrower {
  name: string;
  occupation: string;
  amount: number;
  grade: "A+" | "A" | "B";
  roi: number;
}

export default function BorrowerCard({
  borrower,
}: {
  borrower: Borrower;
}) {
  const colors = {
    "A+": "text-[#C7F5D9]",
    A: "text-[#F6E7C8]",
    B: "text-[#FFD98A]",
  };

  return (
    <motion.div whileHover={{ y: -4 }}>
      <GlassCard className="p-5">
        <div className="flex items-start justify-between">
          <div className="flex gap-4">
            <div className="flex h-14 w-14 items-center justify-center rounded-2xl bg-[#F6E7C8]/10 text-lg text-[#F6E7C8]">
              {borrower.name[0]}
            </div>

            <div>
              <h3 className="text-lg text-white">
                {borrower.name}
              </h3>

              <p className="text-sm text-white/40">
                {borrower.occupation}
              </p>
            </div>
          </div>

          <div
            className={`rounded-full bg-white/5 px-3 py-1 text-sm ${colors[borrower.grade]}`}
          >
            {borrower.grade}
          </div>
        </div>

        <div className="mt-6 grid grid-cols-2 gap-4">
          <div>
            <p className="text-xs text-white/40">
              Loan Amount
            </p>

            <h4 className="mt-1 text-xl text-white">
              ₹{new Intl.NumberFormat("en-IN").format(borrower.amount)}
            </h4>
          </div>

          <div>
            <p className="text-xs text-white/40">
              Expected ROI
            </p>

            <h4 className="mt-1 text-xl text-[#C7F5D9]">
              {borrower.roi}%
            </h4>
          </div>
        </div>

        <div className="mt-6 flex items-center justify-between">
          <div className="flex items-center gap-2 text-sm text-white/50">
            <ShieldCheck size={16} />
            AI Verified
          </div>

          <button className="flex items-center gap-2 rounded-xl bg-[#F6E7C8] px-4 py-2 text-sm font-medium text-black transition hover:scale-105">
            <TrendingUp size={16} />
            Fund
          </button>
        </div>
      </GlassCard>
    </motion.div>
  );
}