import { motion } from "framer-motion";
import { Building2, User } from "lucide-react";

interface TransferAnimationProps {
  fromLabel: string;
  toLabel: string;
  isAnimating: boolean;
}

export default function TransferAnimation({
  fromLabel,
  toLabel,
  isAnimating,
}: TransferAnimationProps) {
  return (
    <div className="relative flex items-center justify-between gap-8 py-12">
      {/* From (Lender) */}
      <motion.div
        initial={{ scale: 1 }}
        animate={isAnimating ? { scale: [1, 0.9, 1] } : {}}
        transition={{ duration: 2, repeat: Infinity }}
        className="flex flex-col items-center gap-4"
      >
        <div className="rounded-full bg-gradient-to-br from-[#C7F5D9] to-[#F6E7C8] p-6">
          <User size={40} className="text-black" />
        </div>
        <p className="text-center text-sm text-white/60">{fromLabel}</p>
      </motion.div>

      {/* Transfer Arrow with Money */}
      <div className="relative h-24 flex-1">
        <svg
          className="absolute inset-0 h-full w-full"
          viewBox="0 0 400 100"
          preserveAspectRatio="none"
        >
          <defs>
            <linearGradient id="arrowGradient" x1="0%" y1="0%" x2="100%">
              <stop offset="0%" stopColor="#C7F5D9" />
              <stop offset="100%" stopColor="#F6E7C8" />
            </linearGradient>
          </defs>

          {/* Main arrow line */}
          <line x1="20" y1="50" x2="380" y2="50" stroke="url(#arrowGradient)" strokeWidth="3" />

          {/* Arrow head */}
          <polygon points="380,50 360,40 360,60" fill="#F6E7C8" />

          {/* Animated dashes */}
          {isAnimating && (
            <>
              <circle cx="100" cy="50" r="6" fill="#C7F5D9" />
              <circle cx="200" cy="50" r="6" fill="#C7F5D9" />
              <circle cx="300" cy="50" r="6" fill="#C7F5D9" />
            </>
          )}
        </svg>

        {/* Money symbol over arrow */}
        {isAnimating && (
          <motion.div
            initial={{ x: 0 }}
            animate={{ x: "100%" }}
            transition={{ duration: 2, repeat: Infinity }}
            className="absolute top-1/2 left-0 -translate-y-1/2 text-3xl font-bold text-[#F6E7C8]"
          >
            ₹
          </motion.div>
        )}
      </div>

      {/* To (Borrower) */}
      <motion.div
        initial={{ scale: 1 }}
        animate={isAnimating ? { scale: [1, 1.1, 1] } : {}}
        transition={{ duration: 2, repeat: Infinity }}
        className="flex flex-col items-center gap-4"
      >
        <div className="rounded-full bg-gradient-to-br from-[#F6E7C8] to-green-500 p-6">
          <Building2 size={40} className="text-black" />
        </div>
        <p className="text-center text-sm text-white/60">{toLabel}</p>
      </motion.div>
    </div>
  );
}
