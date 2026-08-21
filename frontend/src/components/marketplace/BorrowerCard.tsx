import { motion } from "framer-motion";
import GlassCard from "../ui/GlassCard";
import PrimaryButton from "../ui/PrimaryButton";
import { RiskBadge } from "./RiskBadge";
import type { BorrowerOpportunity } from "../../types/marketplace";

interface Props {
  borrower: BorrowerOpportunity;
}

export default function BorrowerCard({
  borrower,
}: Props) {
  return (
    <motion.div
      whileHover={{
        y: -6,
        rotateX: 2,
        scale: 1.01,
      }}
      transition={{ duration: 0.25 }}
    >
      <GlassCard className="p-6">
        <div className="flex items-start justify-between">
          <div>
            <h2 className="text-2xl font-light text-white">
              {borrower.title}
            </h2>

            <p className="mt-1 text-white/40">
              {borrower.purpose}
            </p>
          </div>

          <RiskBadge score={borrower.aiRiskScore} />
        </div>

        <div className="mt-6 grid grid-cols-3 gap-3">
          <div>
            <p className="text-xs text-white/40">Amount</p>
            <h3 className="mt-1 text-[#F6E7C8]">
              ₹
              {(
                borrower.amount / 100000
              ).toFixed(1)}
              L
            </h3>
          </div>

          <div>
            <p className="text-xs text-white/40">Tenure</p>
            <h3 className="mt-1 text-white">
              {borrower.tenureMonths}m
            </h3>
          </div>

          <div>
            <p className="text-xs text-white/40">Yield</p>
            <h3 className="mt-1 text-[#C7F5D9]">
              {borrower.interestRate}%
            </h3>
          </div>
        </div>

        <div className="mt-6">
          <PrimaryButton>Invest Now</PrimaryButton>
        </div>
      </GlassCard>
    </motion.div>
  );
}